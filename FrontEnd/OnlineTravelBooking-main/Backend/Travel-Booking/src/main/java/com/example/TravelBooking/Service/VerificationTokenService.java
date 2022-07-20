package com.example.TravelBooking.Service;

import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Entity.VerificationToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface VerificationTokenService {
    void deleteById(Long id);
    VerificationToken findByUser(User user);
    Boolean existsByUser(User user);
    void deleteByUser(User user);
}
