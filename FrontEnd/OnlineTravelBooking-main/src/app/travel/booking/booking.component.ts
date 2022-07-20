import { Flight } from 'src/app/data/flight';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { BookingService } from 'src/app/services/booking-service/booking.service';


@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css'],
})
export class BookingComponent implements OnInit {
  flightId: any;
  seatsBooked: number = 1;
  rzp1:any;


  flightBookingModel = {
    userEmail: 'pravin@gmail.com',
    flightId: '',
    seats: 1,
  };


options = {
  "key": "rzp_test_CYteNBuo9jA2t3", // Enter the Key ID generated from the Dashboard
  "amount": "", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
  "currency": "INR",
  "name": "Make My Trip",
  "description": "Test Transaction",
  "image": "https://example.com/your_logo",
  "order_id": "", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
  "callback_url": "https://eneqd3r9zrjok.x.pipedream.net/",
  "prefill": {
      "name": "Freddie Kruger",
      "email": "alatirahad268@gmail.com",
      "contact": "7980300629"
  },
  "notes": {
      "address": "Razorpay Corporate Office"
  },
  "theme": {
      "color": "#3399cc"
  }
};

  flight: Flight = new Flight();

  ngOnInit(): void {}

  constructor(
    private route: ActivatedRoute,
    private _snack: MatSnackBar,
    private bookingService: BookingService,
    private router: Router,
  ) {
    this.flightId = this.route.snapshot.params['flightId'];
    this.bookingService.getFlightById(this.flightId).subscribe((data: any) => {
      this.flight = data;
    });
  }


  confirmBooking() {
    // seat validation
    if ( this.seatsBooked > (this.flight.flightTotalSeats - this.flight.flightBookedSeats)) {
      this._snack.open(
        'Only ' +
          (this.flight.flightTotalSeats - this.flight.flightBookedSeats) +
          ' seats available!',
        'ok',
        {
          duration: 3000,
        }
      );
    } else if (this.seatsBooked <= 0){
      this._snack.open(
        "Please select one seat or more ",
        'ok',
        {
          duration: 3000,
        }
      );
    }
    
    else {
      if (localStorage.getItem("isEnabled") === "true") {
        
      
      let email = localStorage.getItem('userEmail');
      if (email === null) {
        this._snack.open(
          "Your Email is invalid ",
          'ok',
          {
            duration: 3000,
          }
        );
          
      } else {
        this.flightBookingModel['userEmail'] = email;
      }
     
      this.flightBookingModel['flightId'] = this.flightId;
      this.flightBookingModel['seats'] = this.seatsBooked;
      this.bookingService.booking(this.flightBookingModel).subscribe((data:any) => {
        if(data!=null){
        this.options.amount = data.amount;
        this.options.order_id = data.id;
        this.rzp1 = new this.bookingService.nativeWindow.Razorpay(this.options);
        this.rzp1.open();
        this.router.navigate(['/travel/flight'])
        }
      });

    } else {
      this._snack.open(
        "You acccount is not authorized", "ok",
        {
          duration: 3000,
        }
      );
    }
  }

  }
}
