package com.example.TravelBooking.Service;


import com.example.TravelBooking.Entity.Flight;
import com.example.TravelBooking.Entity.FlightBooking;
import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Model.FlightBookingModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface FlightBookingService {
    String booking(User user, Flight flight, FlightBookingModel flightBookingModel);
    String updateSeating(Long id, int seats);
    String cancelBooking(Long id);
    FlightBooking findById(Long id);
    List<FlightBooking> findAllByUser(Long userId);
    void alertUsers(Long flightId);
}
