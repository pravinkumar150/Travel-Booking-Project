package com.example.TravelBooking.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_FLIGHT_BOOKING")
    )
    private User user;
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, foreignKey = @ForeignKey(name = "FK_FLIGHT"))
    private Flight flight;
    private Integer seats;
    private Boolean status;
    private Date date;
}
