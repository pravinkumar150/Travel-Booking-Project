package com.example.TravelBooking.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class PasswordResetToken {
    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String token;
    private Date expirationTime;
    @OneToOne
    @JoinColumn(name = "userId",nullable = false,foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_TOKEN"))
    private User user;
    public PasswordResetToken(User user, String token){
        this.user = user;
        this.token = token;
        this.expirationTime = calculateExpirationDate();
    }
    public PasswordResetToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate();
    }

    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }

}

