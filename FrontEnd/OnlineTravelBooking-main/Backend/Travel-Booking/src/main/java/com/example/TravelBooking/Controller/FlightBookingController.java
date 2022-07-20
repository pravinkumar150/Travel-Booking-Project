package com.example.TravelBooking.Controller;

import com.example.TravelBooking.Entity.Flight;
import com.example.TravelBooking.Entity.FlightBooking;
import com.example.TravelBooking.Entity.User;
import com.example.TravelBooking.Model.FlightBookingModel;
import com.example.TravelBooking.Model.PaymentModel;
import com.example.TravelBooking.Service.FlightBookingService;
import com.example.TravelBooking.Service.FlightService;
import com.example.TravelBooking.Service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/api/flight-booking/")
@CrossOrigin("*")
public class FlightBookingController {
    @Autowired
    private FlightBookingService flightBookingService;
    @Autowired
    private UserService userService;
    @Autowired
    private FlightService flightService;
    @PostMapping("/add")
    public PaymentModel booking(@RequestBody FlightBookingModel flightBookingModel) {
        User user = userService.findByUserEmail(flightBookingModel.getUserEmail());
        Flight flight = flightService.findById(flightBookingModel.getFlightId());
        int amount = (int)(flightBookingModel.getSeats() * flight.getFlightFare());
        if (flight != null && user != null) {
            flightBookingService.booking(user, flight, flightBookingModel);
            PaymentModel paymentModel = new PaymentModel();
            Order response = makePaymentController(amount);
            paymentModel.setAmount(response.get("amount"));
            paymentModel.setId(response.get("id"));
            return paymentModel;
        }
        return null;
    }
    @DeleteMapping("/delete/{id}")
    public String cancelBooking(@PathVariable("id") Long id){
        return flightBookingService.cancelBooking(id);
    }
    @PutMapping("/update-seating/{id}")
    public String updateSeats(@PathVariable("id") Long id, @RequestBody FlightBookingModel flightBookingModel){
        FlightBooking flightBooking = flightBookingService.findById(id);
        User user = userService.findByUserEmail(flightBookingModel.getUserEmail());
        if (flightBooking != null && user != null && user.getEnabled() && user.equals(flightBooking.getUser())) {
            flightBookingService.updateSeating(id, flightBookingModel.getSeats());
            return "Your Request Successfully Handled.";
        }
        return "Invalid Request";
    }
    @GetMapping("/get/{id}")
    public List<FlightBooking> findAllByUser(@PathVariable("id") Long userId) {
        List<FlightBooking> flightBookings=  flightBookingService.findAllByUser(userId);
        if (flightBookings.size() > 0) return flightBookings;
        return null;
    }
    public Order makePaymentController(int amount){
        Order order = null;
        try {
            RazorpayClient client = new RazorpayClient("rzp_test_CYteNBuo9jA2t3", "xnOsBuesrtUX7yQb2aLYoIRU");            JSONObject ob = new JSONObject();
            ob.put("amount", amount*100);
            ob.put("currency", "INR");
            ob.put("receipt", "sp_732");
            order=client.orders.create(ob);
            System.out.println(order);
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
        return order;
    }
    @PostMapping("/alert/{id}")
    public void alertPassengers(@PathVariable("id") Long flightId) {flightBookingService.alertUsers(flightId);}

}
