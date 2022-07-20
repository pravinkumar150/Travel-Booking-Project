package com.example.TravelBooking.Repository;

import com.example.TravelBooking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);
    void deleteByUserId(Long userId);
    User findByUserId(Long userId);
    boolean existsByUserEmail(String userEmail);
}