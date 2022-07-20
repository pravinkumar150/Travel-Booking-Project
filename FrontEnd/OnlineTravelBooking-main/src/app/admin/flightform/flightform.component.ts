import { Subject } from 'rxjs';
import { FlightService } from './../../services/flight-service/flight.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal from 'sweetalert2';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-flight-form',
  templateUrl: './flightform.component.html',
  styleUrls: ['./flightform.component.css']
})
export class FlightFormComponent implements OnInit {

  dtOptions: DataTables.Settings = {};
  // dtTrigger: Subject<any> = new Subject<any>();

  public flight = {
    // flightId: '',
    flightName: '',
    flightSource: '',
    flightDepartureDate: '',
    flightDestination: '', 
    flightArrivalDate: '',
    flightFare: '',
    flightAvailability: '',
    flightTotalSeats: '',
    flightBookedSeats: '',
    flightLogoUrl: ''
  }
  
  reactiveForm : FormGroup;

  constructor(private flightService:FlightService, private formBuilder:FormBuilder, private _snackBar:MatSnackBar, private router: Router) { 
    this.reactiveForm = this.formBuilder.group({
      flightName: new FormControl(null, [Validators.required]),
      flightSource: new FormControl(null, [Validators.required]),
      flightDestination: new FormControl(null, [Validators.required]),
      flightDepartureDate: new FormControl(null, [Validators.required]),
      flightArrivalDate: new FormControl(null, [Validators.required]),
      flightFare: new FormControl(null, [Validators.required]),
      flightAvailability: new FormControl(null, [Validators.required]),
      flightTotalSeats: new FormControl(null, [Validators.required]),
      flightBookedSeats: new FormControl(null, [Validators.required]),
      flightLogoUrl: new FormControl(null, [Validators.required])
    })
  }
  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
    };
  }

  get f() {
    return this.reactiveForm.controls;
  }

  onFormSubmit() {
    if (this.reactiveForm.invalid) {
      console.log("Invalid");
      return;
    }
    console.log(this.flight);
    this.flight.flightName=this.reactiveForm.value.flightName;
    this.flight.flightSource=this.reactiveForm.value.flightSource;
    this.flight.flightDepartureDate=this.reactiveForm.value.flightDepartureDate; 
    this.flight.flightDestination=this.reactiveForm.value.flightDestination; 
    this.flight.flightArrivalDate=this.reactiveForm.value.flightArrivalDate; 
    this.flight.flightFare=this.reactiveForm.value.flightFare; 
    this.flight.flightAvailability=this.reactiveForm.value.flightAvailability; 
    this.flight.flightTotalSeats=this.reactiveForm.value.flightTotalSeats; 
    this.flight.flightBookedSeats=this.reactiveForm.value.flightBookedSeats; 
    this.flight.flightLogoUrl=this.reactiveForm.value.flightLogoUrl; 
    
    console.log("Data is Valid");
    this.flightService.add(this.flight).subscribe();
    Swal.fire(`Flight Added.`);
    this.router.navigate(['/admin/flight']);
  
  }

}
