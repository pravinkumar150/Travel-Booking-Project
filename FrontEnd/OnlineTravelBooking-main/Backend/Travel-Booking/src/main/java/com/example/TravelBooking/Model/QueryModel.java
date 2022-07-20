package com.example.TravelBooking.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryModel {
    private String flightSource;
    private String flightDestination;
    private Date flightDepartureDate;
    private Date flightArrivalDate;
}
