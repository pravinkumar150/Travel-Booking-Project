package com.example.TravelBooking.Service;


import com.example.TravelBooking.Email.EmailSenderService;
import com.example.TravelBooking.Entity.Flight;
import com.example.TravelBooking.Entity.FlightBooking;
import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Model.FlightBookingModel;
import com.example.TravelBooking.Repository.FlightBookingRepository;
import com.example.TravelBooking.Repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class FlightBookingServiceImpl implements FlightBookingService{
    @Autowired
    private FlightBookingRepository flightBookingRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private FlightService flightService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailService;

    @Override
    @Transactional
    public String booking(User user, Flight flight, FlightBookingModel flightBookingModel) {
        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setFlight(flight);
        log.info("Flight getting retrived" + flight.toString());
        Timestamp bookedTime = new Timestamp(System.currentTimeMillis());
        flightBooking.setDate(bookedTime);
        flightBooking.setUser(user);
        flightBooking.setSeats(flightBookingModel.getSeats());
        if (user.getEnabled()) { // checking he logged in or not
            if (flight.getFlightAvailability()) { // check whether this flight is having enough seats or not
                int bookedSeats = flight.getFlightBookedSeats(); // get the booked seats
                int availableSeats = flight.getFlightTotalSeats() - bookedSeats; // calculate the available seats
                Boolean availability = (availableSeats - flightBooking.getSeats() >= 0); // update the availability
                if (flightBooking.getSeats() <= availableSeats) {
                    flightBooking.setStatus(true);
                    flightBookingRepository.save(flightBooking);
                    // generate Email to the passenger about booking.
                    String toEmail = user.getUserEmail();
                    String subject = "Your Booking Successfully Handled";
                    String Body = "" +
                            "<h1> Name: " + user.getUserName() + "</h1>, " +
                            "<p> Address: <strong>" + user.getUserAddress() + "</strong></p>, " +
                            "<p> From : <strong>" + flight.getFlightSource()  + "</strong></p> " +
                            "<p> To : <strong>" + flight.getFlightDestination()  + "</strong></p> " +
                            "<p> Date : <strong>" + flight.getFlightDepartureDate().getTime()  + "</strong></p> " +
                            "<p> Price : <strong>" + flight.getFlightFare() * flightBookingModel.getSeats()  + "</strong></p> " +
                            "<p> From : <strong>" + flight.getFlightSource()  + "</strong></p> " +
                            "<img src = " + flight.getFlightLogoUrl() + " alt = " + flight.getFlightName() + "/>";
                    emailService.sendSimpleEmail(toEmail, Body, subject);
                    flight.setFlightBookedSeats(bookedSeats+flightBooking.getSeats());
                    flight.setFlightAvailability(availability);
                    flightService.update(flight.getFlightId(), flight);
                    return "Congrats Your Booking request successfully granted.";
                } else {
                    return "Sorry We don't have Enough seats to satisfy your need.";
                }
            } else {
                return "Flight is unavailable now.";
            }
        }
        return "User Must be an Authenticated User";
    }
    @Override
    public String updateSeating(Long id, int seats) {
        FlightBooking flightBooking = null;
        Optional<FlightBooking> fb = flightBookingRepository.findById(id);
        if (fb.isPresent()) flightBooking = fb.get(); else return "Flight Id is not Valid";
        Flight flight = flightBooking.getFlight();
        int totalBookedSeats = flight.getFlightBookedSeats()-flightBooking.getSeats();
        if (totalBookedSeats+seats <= flight.getFlightTotalSeats()) {
            flightBooking.setSeats(seats);
            flightBookingRepository.save(flightBooking);
            flight.setFlightBookedSeats(totalBookedSeats+seats);
            flightService.update(flight.getFlightId(), flight);
        }
        return "Success";
    }
    @Override
    @Transactional
    public String cancelBooking(Long id) {
        FlightBooking flightBooking = null;
        Optional<FlightBooking> fb = flightBookingRepository.findById(id);
        if (fb.isPresent()) flightBooking = fb.get(); else return "FlightBooking Id is not Valid";
        Flight flight = flightBooking.getFlight();
//        User user = flightBooking.getUser();
//        if (user.equals(userService.findByUserEmail(email)) && user.getEnabled()) {
            if (flight != null) { // He already has the booking or not
                int seats = flightBooking.getSeats();
                int bookedSeats = flight.getFlightBookedSeats();
//                Boolean availability = (bookedSeats - seats) < flight.getFlightTotalSeats();
                flightBookingRepository.deleteById(id);
                flight.setFlightBookedSeats(bookedSeats - seats);
//                flight.setFlightAvailability(availability);
                flightService.update(flight.getFlightId(), flight);
                return "Your Request Handles Successfully.";
            } else {
                return "You Don't have Booking With this Id";
            }
//        }
//        return "Invalid request";
    }
    @Override
    public FlightBooking findById(Long id) {
        Optional<FlightBooking> fb = flightBookingRepository.findById(id);
        return fb.isPresent() ? fb.get(): null;
    }
    @Override
    public List<FlightBooking> findAllByUser(Long userId) {
        User user = userService.findByUserId(userId);
        return flightBookingRepository.findAllByUser(user);
    }
    @Override
    public void alertUsers(Long flightId) {
        Flight flight = (flightRepository.findById(flightId).isPresent()) ? flightRepository.findById(flightId).get() : null;
        List<String> email = flightBookingRepository.findUsersByFlight(flightId);
        if (email.size() > 0) {
            for (String e: email) {
                emailService.sendSimpleEmail(e, "", "We are having some technical glitches in the flight which you have booked to enjoy your" +
                        "travel. It will consume your time so kindly make your plan according to that.");
            }
        }
    }



}
