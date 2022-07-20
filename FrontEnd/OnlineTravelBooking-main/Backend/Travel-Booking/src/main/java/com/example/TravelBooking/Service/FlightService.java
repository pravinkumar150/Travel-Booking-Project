package com.example.TravelBooking.Service;

import com.example.TravelBooking.Entity.Flight;
import com.example.TravelBooking.Model.QueryModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface FlightService {
    Flight register(Flight flight);
    Flight update(Long id, Flight flight);
    int delete(Long flightId);
    List<Flight> getAll();
    Flight findById(Long flightId);
    List<Flight> getFlightsByQuery(QueryModel queryModel);
    Boolean existsByFlight(Flight flight);
    void deleteByFlight(Flight flight);
}
