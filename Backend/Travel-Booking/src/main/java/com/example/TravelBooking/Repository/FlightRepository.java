package com.example.TravelBooking.Repository;

import com.example.TravelBooking.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByFlightSourceContainingAndFlightDestinationContaining(String flightSource, String flightDestination);


}
