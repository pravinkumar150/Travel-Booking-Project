package com.example.TravelBooking.Controller;//package com.example.OnlineBooking.Controller;

import com.example.TravelBooking.Email.EmailSenderService;
import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Entity.VerificationToken;
import com.example.TravelBooking.Event.RegistrationCompleteEvent;
import com.example.TravelBooking.Model.LoginModel;
import com.example.TravelBooking.Model.PasswordModel;
import com.example.TravelBooking.Model.StringResponse;
import com.example.TravelBooking.Model.UserModel;
import com.example.TravelBooking.Service.PasswordResetTokenService;
import com.example.TravelBooking.Service.UserService;
import com.example.TravelBooking.Service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/users/")
@CrossOrigin("*")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private EmailSenderService emailSenderService;
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.register(userModel);
        if (user == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        VerificationToken verificationToken = verificationTokenService.findByUser(user);
        String r = applicationUrl(request) + "/verifyRegistration?token=" + verificationToken.getToken();
        emailSenderService.sendSimpleEmail(user.getUserEmail(), r, "Verification Link");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public User login(@RequestBody LoginModel loginModel) {
        User user = userService.findByUserEmail(loginModel.getUserEmail());
        if (user != null && userService.checkIfValidOldPassword(user, loginModel.getUserPassword())) return user;
        return null;
    }
    @PutMapping("/update/{id}")
    public User update(@PathVariable("id") Long id, @RequestBody UserModel userModel) {
        return userService.updateById(id, userModel);
    }
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User Verified Successfully";
        }
        return "Bad User";
    }
    @GetMapping("/resendVerification")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link sent";
    }
    @PostMapping("/resetPassword")
    public StringResponse resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request){
        // grab the user by email from the request
        // if user exist, then check the password which is matching with the user's password
        // if step 2 return true then set the new password to the user and save the user.
        User user = userService.findByUserEmail(passwordModel.getUserEmail());
        String url = "";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetToken(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
            return new StringResponse(url);
        }
        return new StringResponse("invalid email");
    }
    @PostMapping("/savePassword")
    public StringResponse savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        // first check the token is valid or not
        // Check the time if token is valid , to do this compare the expiration time of the
        // PasswordResetToken object and current time.
        // Then retrieve the user by token from PasswordResetToken and change the password
        String result = userService.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("valid")){
            return new StringResponse("Invalid Token");
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return new StringResponse("Password Reset Successful");
        } else {
            return new StringResponse("Invalid Token");
        }

    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
        log.info("User Email : " + passwordModel.getUserEmail());
        log.info("User Password : " + passwordModel.getNewPassword());
        User user = userService.findByUserEmail(passwordModel.getUserEmail());
        if (user != null) {
            if (!userService.checkIfValidOldPassword(user, passwordModel.getOldPassword())){
                return "Invalid Old Password";
            }
            //set new Password
            userService.changePassword(user, passwordModel.getNewPassword());
            return "Password change Successfully";
        }
        return "Invalid Email";

    }
    @DeleteMapping("/delete/{id}")
    public String remove(@PathVariable("id") Long userId) {
        User user = userService.findByUserId(userId);
        if (user == null) return "No User with this Id";
        Boolean verificationToken = verificationTokenService.existsByUser(user);
        Boolean passwordResetTokens = passwordResetTokenService.existsByUser(user);
        if (verificationToken && passwordResetTokens) {
            verificationTokenService.deleteByUser(user);
            passwordResetTokenService.deleteByUser(user);
            userService.deleteByUserId(userId);
            return "User Removed Successfully";
        }
        if (verificationToken){
            verificationTokenService.deleteById(userId);
        }
        if (passwordResetTokens){
            passwordResetTokenService.deleteByUser(user);
        }
        userService.deleteByUserId(userId);
        return "User Removed Successfully";
    }
    @GetMapping("get/{email}")
    public User getUserByEmail(@PathVariable("email") String email) {
        return userService.findByUserEmail(email);
    }
    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savePassword?token=" + token;
        //sendVerificationEmail()
        log.info("Click the link to reset your password {} " + url);
        return url;
    }
    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        //sendVerificationEmail()
        log.info("Click the link to verify your account {} " + url);
    }
    private String applicationUrl(HttpServletRequest request) {
        log.info("Context Path : " + request.getContextPath());
        return "http://" + request.getServerName() + ":" + request.getServerPort() +"/api/users"+request.getContextPath();
    }



}
