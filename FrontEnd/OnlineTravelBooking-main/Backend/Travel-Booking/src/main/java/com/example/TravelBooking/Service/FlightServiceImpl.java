package com.example.TravelBooking.Service;

import com.example.TravelBooking.Entity.Flight;
import com.example.TravelBooking.Model.QueryModel;
import com.example.TravelBooking.Repository.FlightBookingRepository;
import com.example.TravelBooking.Repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class FlightServiceImpl implements FlightService{
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private FlightBookingRepository flightBookingRepository;
    @Override
    public Flight register(Flight flight) {
        if (flight != null) flightRepository.save(flight);
        return flight;
    }
    @Override
    public Flight update(Long id, Flight flight) { //Warning:(26, 50) 'Optional.get()' without 'isPresent()' check
        Flight f = flightRepository.findById(id).get();
        if (! "".equalsIgnoreCase(flight.getFlightName())) {
            f.setFlightName(flight.getFlightName());
        }
        if (! "".equalsIgnoreCase(flight.getFlightSource())) {
            f.setFlightSource(flight.getFlightSource());
        }
        if (Objects.nonNull(f.getFlightDestination()) && ! "".equalsIgnoreCase(flight.getFlightDestination())) {
            f.setFlightDestination(flight.getFlightDestination());
        }
        if (Objects.nonNull(f.getFlightArrivalDate()) && Objects.nonNull(flight.getFlightArrivalDate())) {
            f.setFlightArrivalDate(flight.getFlightArrivalDate());
        }
        if (Objects.nonNull(f.getFlightFare()) && Objects.nonNull(flight.getFlightFare())) {
            f.setFlightFare(flight.getFlightFare());
        }

        if (Objects.nonNull(f.getFlightAvailability()) && Objects.nonNull(flight.getFlightAvailability())) {
            f.setFlightAvailability(flight.getFlightAvailability());
        }
        if (Objects.nonNull(f.getFlightTotalSeats()) && Objects.nonNull(flight.getFlightTotalSeats())) {
            f.setFlightTotalSeats(flight.getFlightTotalSeats());
        }
        if (Objects.nonNull(f.getFlightLogoUrl()) && !"".equalsIgnoreCase(flight.getFlightLogoUrl())) {
            f.setFlightLogoUrl(flight.getFlightLogoUrl());
        }
        flightRepository.save(f);
        return f;
    }
    @Override
    public int delete(Long flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        Boolean isHavingPassengers = false;
        if (flight.isPresent()) {
            isHavingPassengers = existsByFlight(flight.get());
            if (isHavingPassengers) {
                deleteByFlight(flight.get());
            }
            flightRepository.deleteById(flightId);
            return 1;
        }
        return 0;
    }
    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }
    @Override
    public Flight findById(Long flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        return flight.isPresent() ? flight.get() : null;
    }
    @Override
    public List<Flight> getFlightsByQuery(QueryModel q) {
        List<Flight> left = flightRepository.findByFlightSourceContainingAndFlightDestinationContaining(q.getFlightSource(), q.getFlightDestination());
        List<Flight> result = new ArrayList<>();
        Timestamp departureDate = new Timestamp(q.getFlightDepartureDate().getTime());
        Timestamp arrivalDate = new Timestamp(q.getFlightArrivalDate().getTime());
        for (Flight flight : left) {
            int t = flight.getFlightDepartureDate().compareTo(departureDate);
            int t2 = flight.getFlightDepartureDate().compareTo(arrivalDate);
            if (t >= 0 && t2 <= 0) result.add(flight);
        }
        return result;
    }
    @Override
    public Boolean existsByFlight(Flight flight) {
        return flightBookingRepository.existsByFlight(flight);
    }
    @Override
    public void deleteByFlight(Flight flight) {
        flightBookingRepository.deleteByFlight(flight);
    }


}
