package com.example.TravelBooking.Service;


import com.example.TravelBooking.Entity.PasswordResetToken;
import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Entity.VerificationToken;
import com.example.TravelBooking.Model.UserModel;
import com.example.TravelBooking.Repository.PasswordResetTokenRepository;
import com.example.TravelBooking.Repository.UserRepository;
import com.example.TravelBooking.Repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Override
    public User register(UserModel userModel) {
        User user = new User();
        user.setUserName(userModel.getUserName());
        user.setUserEmail(userModel.getUserEmail());
        user.setUserDOB(userModel.getUserDOB());
        user.setUserAddress(userModel.getUserAddress());
        if (userModel.checkPassword().equals("Password Invalid")) {
            return null;
        }
        user.setUserPassword(passwordEncoder.encode(userModel.checkPassword()));
        user.setUserGender(userModel.getUserGender());
        user.setUserNation(userModel.getUserNation());
        user.setUserPhoneNo(userModel.getUserPhoneNo());
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            return null;
        }
        userRepository.save(user);
        return user;
    }
    @Override
    public void saveVerificationTokenforUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }
    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) return "invalid";
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "Expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken= verificationTokenRepository.findByToken(oldToken);
        if (verificationToken != null) {
            verificationToken.setToken(UUID.randomUUID().toString());
            verificationTokenRepository.save(verificationToken);
        }
        return verificationToken;
    }
    @Override
    public User findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }
    @Override
    public void createPasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
    }
    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) return "Invalid Token";
        Calendar calendar = Calendar.getInstance();
        if (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "Expired";
        }
        return "Valid";
    }
    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }
    @Override
    public void changePassword(User user, String newPassword) {
        user.setUserPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    @Override
    public boolean checkIfValidOldPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getUserPassword());
    }
    @Override
    public User findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }
    @Override
    public void deleteByUserId(Long userId) {
        userRepository.deleteByUserId(userId);
    }
    @Override
    public User updateById(Long id, UserModel userModel) {
        User user = userRepository.findByUserId(id);
        if (user != null) {
            if (!"".equalsIgnoreCase(userModel.getUserName())) user.setUserName(userModel.getUserName());

            if (!"".equalsIgnoreCase(userModel.getUserEmail())) user.setUserEmail(userModel.getUserEmail());

            if (!"".equalsIgnoreCase(userModel.getUserAddress())) user.setUserAddress(userModel.getUserAddress());

            if (!"".equalsIgnoreCase(userModel.getUserGender())) user.setUserGender(userModel.getUserGender());

            if (Objects.nonNull(userModel.getUserDOB())) user.setUserDOB(userModel.getUserDOB());

            if (!"".equalsIgnoreCase(userModel.getUserPhoneNo())) user.setUserPhoneNo(userModel.getUserPhoneNo());

            if (!"".equalsIgnoreCase(userModel.getUserNation())) user.setUserNation(userModel.getUserNation());

            userRepository.save(user);
        }

        return user;
    }
}
