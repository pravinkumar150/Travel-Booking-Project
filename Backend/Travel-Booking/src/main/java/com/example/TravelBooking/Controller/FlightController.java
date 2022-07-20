package com.example.TravelBooking.Controller;

import com.example.TravelBooking.Entity.Flight;
import com.example.TravelBooking.Model.QueryModel;
import com.example.TravelBooking.Service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/flights/")
@CrossOrigin("*")
@Slf4j
public class FlightController {
    @Autowired
    private FlightService flightService;
    @PostMapping("/register")
    public Flight register(@RequestBody Flight flight) {
        return flightService.register(flight);
    }
    @PutMapping("/update/{id}")
    public Flight update(@RequestBody Flight flight, @PathVariable("id") Long flightId){
       return  flightService.update(flightId, flight);
    }
    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable("id") Long flightId) {
        return flightService.delete(flightId);
    }
    @GetMapping("/all")
    public List<Flight> getAll(){
        return flightService.getAll();
    }
    @GetMapping("/get/{id}")
    public Flight getFlightById(@PathVariable("id") Long flightId) {
        return flightService.findById(flightId);

    }
    @PostMapping("/get/query")
    public List<Flight> getFlightsByQuery(@RequestBody QueryModel queryModel) {
        return flightService.getFlightsByQuery(queryModel);
    }


}
