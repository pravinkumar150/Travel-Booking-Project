package com.example.TravelBooking.Repository;

import com.example.TravelBooking.Entity.Flight;
import com.example.TravelBooking.Entity.FlightBooking;
import com.example.TravelBooking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> findAllByUser(User user);
    Boolean existsByFlight(Flight flight);
    void deleteByFlight(Flight flight);
    @Modifying
    @Query(
            nativeQuery = true,
            value = "select distinct(user_email) from booking.user as u left join booking.flight_booking as f on u.user_id = f.user_id where f.flight_id = :flight_id "
    )
    List<String> findUsersByFlight(@Param("flight_id") Long id);


}

