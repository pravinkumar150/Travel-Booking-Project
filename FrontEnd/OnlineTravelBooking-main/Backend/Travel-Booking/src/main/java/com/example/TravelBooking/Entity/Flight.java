package com.example.TravelBooking.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    private String flightName;
    private String flightSource;
    private String flightDestination;
    private Timestamp flightDepartureDate;
    private Timestamp flightArrivalDate;
    private Float flightFare;
    private Boolean flightAvailability = true;
    private Integer flightTotalSeats ;
    private Integer flightBookedSeats = 0;
    private String flightLogoUrl;

}