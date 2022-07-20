package com.example.TravelBooking.Model;

import lombok.Data;

@Data
public class PasswordModel {
    private String userEmail;
    private String oldPassword;
    private String newPassword;
}

