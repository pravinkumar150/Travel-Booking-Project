import { Flight } from 'src/app/data/flight';
import { Component, OnInit } from '@angular/core';
import { FlightService } from 'src/app/services/flight-service/flight.service';
import { AdminService } from 'src/app/services/admin-service/admin.service';
import {AfterViewInit, ViewChild} from '@angular/core';
import {MatSort, Sort} from '@angular/material/sort';
import { Subject } from 'rxjs';
import {LiveAnnouncer} from '@angular/cdk/a11y';
import {MatTableDataSource} from '@angular/material/table';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-flight',
  templateUrl: './admin-flight.component.html',
  styleUrls: ['./admin-flight.component.css']
})
export class AdminFlightComponent implements OnInit {


  flights:Flight[] =[]
  dtOptions: any;
  // dataSource = new MatTableDataSource(this.flights);

  constructor(private flightService:FlightService, private adminService: AdminService, private _liveAnnouncer: LiveAnnouncer, private router: Router) { 
    this.flightService.getAll().subscribe(flights=> {
      this.flights=flights;
    })
  }


  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
    };
  }


  add(flight:any){
    this.adminService.add(flight).subscribe();
  }

  delete(flightId:any){
    console.log(flightId);
    this.adminService.delete(flightId).subscribe();
    window.location.reload();
  }

  // update(flightId:any, flight:any){
  //   this.adminService.update(flightId, flight).subscribe();
  // }
  update(flightId: any) {
    this.flightService.sendId(flightId);
    this.router.navigate(['/admin/flight/update']);
    // this
  }



}
