import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { param } from 'jquery';
import { Flight } from 'src/app/data/flight';
import baseURL from '../baseURL';


 // razorpay
 function _window():any {
  return window;
}

@Injectable({
  providedIn: 'root'
})  
export class BookingService {

  email: any;
  flight: any;
  flightId: any;

 
  constructor(private http: HttpClient, private router: Router) { 
    router: ActivatedRoute;
  }

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    // this.router.
    // this.router.queryParams.subscribe(param => {
    //   this.flightId = param['flightId'];
    // })
  }

  // http://localhost:8080/api
  //http://localhost:4200/booking/3

  getFlightById(id: any) {
    console.log("Id "+ id);
    console.log(baseURL+"/flights/get/"+id);
    this.flight = this.http.get<Flight>(baseURL+"/flights/get/"+id); //http://localhost:8080/api/flights/get/1
    localStorage.setItem("flight", this.flight);
    return this.flight;
  }

//   {
//     "userEmail" : "pravin@gmail.com",
//     "flightId" : 2,
//     "date" : "2022-07-11",
//     "seats": 3
// }

  booking(flightBookingModel: any) {
    console.log(flightBookingModel['userEmail'])
    console.log(flightBookingModel['flightId'])
    console.log(flightBookingModel['seats'])

    return this.http.post(baseURL + '/flight-booking/add', flightBookingModel); //http://localhost:8080/api/flight-booking/add
     
  }

  getUserEmail() {
    // this.loggedUser = localStorage.getItem("userDetails");
    // this.loggedUser = JSON.parse(this.loggedUser);
    return localStorage.getItem("userEmail");
    // console.log(this.)
    // this.email = JSON.parse(this.email).userEmail;
    // return this.email;
    // return localStorage.getItem("userDetails").userEmail;
  }

  // razorpay
  get nativeWindow():any {
    return _window();
  }
}
