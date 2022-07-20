import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { data } from 'jquery';
import { Flight } from 'src/app/data/flight';
import { FlightService } from 'src/app/services/flight-service/flight.service';

@Component({
  selector: 'app-update-flight',
  templateUrl: './update-flight.component.html',
  styleUrls: ['./update-flight.component.css']
})
export class UpdateFlightComponent implements OnInit {
  
  // public flight = {
  //   flightName: '',
  //   flightSource: '',
  //   flightDepartureDate: '',
  //   flightDestination: '', 
  //   flightArrivalDate: '',
  //   flightFare: '',
  //   flightAvailability: '',
  //   flightTotalSeats: '',
  //   flightBookedSeats: '',
  //   flightLogoUrl: ''
  // }
  flight: Flight = new Flight;
  id: any;
  reactiveForm : FormGroup;
  source = '';

  constructor(private flightService:FlightService, private formBuilder:FormBuilder, private _snackBar:MatSnackBar, private router: Router) { 
    console.log("Update FLight")
    this.flightService.id.asObservable().subscribe(data => this.id = data);
    console.log("Flight Id from update-flight : " + this.id)
    this.flightService.getFlightById(this.id).subscribe((data: Flight)=> {
      this.flight = data;
      console.log(this.flight);
    });
    console.log(" Flight from update flight : " + this.flight.flightName)
    this.reactiveForm = this.formBuilder.group({
      flightName: new FormControl('', [Validators.required]),
      flightSource: new FormControl(this.flight.flightSource+"", [Validators.required]),
      flightDestination: new FormControl(this.flight.flightDestination+"", [Validators.required]),
      flightDepartureDate: new FormControl(this.flight.flightDepartureDate+"", [Validators.required]),
      flightArrivalDate: new FormControl(this.flight.flightArrivalDate+"", [Validators.required]),
      flightFare: new FormControl(this.flight.flightFare, [Validators.required]),
      flightAvailability: new FormControl(this.flight.flightAvailability, [Validators.required]),
      flightTotalSeats: new FormControl(this.flight.flightTotalSeats, [Validators.required]),
      flightBookedSeats: new FormControl(this.flight.flightBookedSeats, [Validators.required]),
      flightLogoUrl: new FormControl(this.flight.flightLogoUrl, [Validators.required])
    })
    // this.reactiveForm.controls['flightName'].setValue(this.flight.flightName);
  }

  ngOnInit(): void {
  }
  
  get f() {
    return this.reactiveForm.controls;
  }
  onFormSubmit(){}

}
