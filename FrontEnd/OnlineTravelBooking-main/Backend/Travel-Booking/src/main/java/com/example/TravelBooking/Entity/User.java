package com.example.TravelBooking.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"userEmail"}),
        name = "user"
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userGender;
    private String userAddress;
    private String userPhoneNo;
    private String userNation;
    private Date userDOB;
    private Boolean enabled = false;
    private Boolean isAdmin = false;

}