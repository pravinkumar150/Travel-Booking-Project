package com.example.TravelBooking.Service;

import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService{
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void deleteByUser(User user) {
        passwordResetTokenRepository.deleteByUser(user);
    }
    @Override
    public Boolean existsByUser(User user) {
        return passwordResetTokenRepository.existsByUser(user);
    }
}
