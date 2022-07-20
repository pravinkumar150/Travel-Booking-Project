package com.example.TravelBooking.Service;
import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Entity.VerificationToken;
import com.example.TravelBooking.Repository.VerificationTokenRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class VerificationTokenServiceImpl implements VerificationTokenService{
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Override
    public void deleteById(Long id) {
        verificationTokenRepository.deleteById(id);
    }
    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }
    @Override
    public Boolean existsByUser(User user) {
        return verificationTokenRepository.existsByUser(user);
    }

    @Override
    public void deleteByUser(User user) {
        verificationTokenRepository.deleteByUser(user);
    }
}
