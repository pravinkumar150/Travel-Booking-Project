package com.example.TravelBooking.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String userName;
    private String userEmail;
    private String userPassword;
    private String matchingPassword;
    private String userGender;
    private String userAddress;
    private String userPhoneNo;
    private String userNation;
    private Boolean enabled;
    private Date userDOB;
    public String checkPassword() {
        if (userPassword.equals(matchingPassword)) return userPassword;
        return "Password Invalid";
    }

}