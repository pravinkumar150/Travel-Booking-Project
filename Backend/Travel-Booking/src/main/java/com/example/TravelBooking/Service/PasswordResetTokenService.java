package com.example.TravelBooking.Service;

import com.example.TravelBooking.Entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface PasswordResetTokenService {
    void deleteByUser(User user);
    Boolean existsByUser(User user);
}
