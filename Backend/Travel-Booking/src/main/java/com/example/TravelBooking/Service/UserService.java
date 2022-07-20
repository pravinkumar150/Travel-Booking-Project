package com.example.TravelBooking.Service;

import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Entity.VerificationToken;
import com.example.TravelBooking.Model.UserModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User register(UserModel userModel);

    void saveVerificationTokenforUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findByUserEmail(String userEmail);

    void createPasswordResetToken(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);

    User findByUserId(Long userId);

    void deleteByUserId(Long userId);

    User updateById(Long id, UserModel userModel);
}
