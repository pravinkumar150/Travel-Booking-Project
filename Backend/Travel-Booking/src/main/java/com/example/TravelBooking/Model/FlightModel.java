package com.example.TravelBooking.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
public class FlightModel {
    private String userEmail;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    private String flightName;
    private String flightSource;
    private String flightDestination;
    private Date flightArrivalTime;
    private Float flightFare;
    private Boolean flightAvailability = true;
    private Integer flightTotalSeats ;
    private Integer flightBookedSeats = 0;
    private String flightLogoUrl;
}
