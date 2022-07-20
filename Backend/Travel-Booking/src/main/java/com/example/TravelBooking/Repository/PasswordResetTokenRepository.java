package com.example.TravelBooking.Repository;

import com.example.TravelBooking.Entity.PasswordResetToken;
import com.example.TravelBooking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    Boolean existsByUser(User user);
    void deleteByUser(User user);
}
