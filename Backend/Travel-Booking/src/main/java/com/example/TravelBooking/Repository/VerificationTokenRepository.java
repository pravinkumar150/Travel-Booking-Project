package com.example.TravelBooking.Repository;

import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
    Boolean existsByUser(User user);
    void deleteByUser(User user);
}
