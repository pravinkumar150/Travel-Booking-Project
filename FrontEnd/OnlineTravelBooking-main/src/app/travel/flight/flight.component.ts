import { FlightService } from 'src/app/services/flight-service/flight.service';
import { Subject } from 'rxjs';
import { Flight } from 'src/app/data/flight';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-flight',
  templateUrl: './flight.component.html',
  styleUrls: ['./flight.component.css']
})
export class FlightComponent implements OnInit {


  userEmail = localStorage.getItem("userId");
  minDate: any;
  maxDate: any;

  // from child
  flights:Flight[]=[];
  filteredFlights:Flight[]=[];
  // subscription?:Subscription;
  id:any;
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();

  source:any;
  destination:any;
  airline:any;


  sourceInp:any;
  destinationInp:any;
  airlineInp:any;
  startDateInp: any;
  endDateInp: any;

  filteredFilghtsBySource:any;
  filteredFlightsByDestination:any;
  public query = {
    flightSource: '',
    flightDestination: '',
    flightDepartureDate: '',
    flightArrivalDate: ''
  };

  flightId: any;

  constructor(private flightService:FlightService, private router: Router) { 
    this.sourceInp = "";
    this.destinationInp = "";
    this.airlineInp = "";
    this.startDateInp = "";
    this.endDateInp = "";
    this.flightService.getAll().subscribe(flights=> {
      this.filteredFlights=flights; 
      this.flights=flights;
    })
    console.log("Retrived Flights "+ JSON.stringify(this.flights));
  }

  ngOnInit(): void {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date();
    this.maxDate = new Date(currentYear + 1, 11, 31);

    // child
    console.log("inside init flight");
    this.filter(null);
    // datatable
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
    };
  }

  filterFlights(){
    this.filteredFlights = (this.sourceInp) ? 
                            this.flights.filter((p:any)=>p.flightSource.toLowerCase().includes(this.sourceInp.toLowerCase()))
                            :this.flights;
    console.log("Started filtering....")
                            
    this.filteredFlights = (this.destinationInp) ? 
                            this.filteredFlights.filter((p:any)=>p.flightDestination.toLowerCase().includes(this.destinationInp.toLowerCase()))
                            :this.filteredFlights;
    
    this.filteredFlights = (this.airlineInp) ? 
                            this.filteredFlights.filter((p:any)=>p.flightName.toLowerCase().includes(this.airlineInp.toLowerCase()))
                            :this.filteredFlights;
  }


  filterFlightsAfterDate(start: any) {
      var dateFrom = this.startDateInp; 

      var d1 = dateFrom.split("/"); // mm/dd/yyyy
      var c = start.split("-"); // yyyy-mm-dd

      var from = new Date(d1[2], parseInt(d1[0])-1, d1[1]);  // -1 because months are from 0 to 11 "02/07/2013";
      // var to   = new Date(d2[2], parseInt(d2[1])-1, d2[0]);
      var check = new Date(c[0], parseInt(c[1])-1, c[2]);
      return (check >= from)
  }

  filterFlightsBeforeDate(start: any) {
    // var dateFrom = this.startDateInp; // startDateInp
    var dateTo = this.endDateInp;
    // var dateCheck = "02/07/2013";

    // var d1 = dateFrom.split("/");
    var d2 = dateTo.split("/");
    var c = start.split("/");

    // var from = new Date(d1[2], parseInt(d1[1])-1, d1[0]);  // -1 because months are from 0 to 11
    var to   = new Date(d2[2], parseInt(d2[1])-1, d2[0]);
    var check = new Date(c[0], parseInt(c[1])-1, c[2]);
    return (check <= to)
}
  

  filter(query:any){
    console.log(query);
    this.filteredFlights = (query) ? 
                            this.flights.filter((p:any)=>p.airline.toLowerCase().includes(query.toLowerCase()))
                            :this.flights;
  }

  filterBySource(source:any){
    this.filteredFlights = (source) ? 
                            this.flights.filter((p:any)=>p.source.toLowerCase().includes(source.toLowerCase()))
                            :this.filteredFlights;
    this.source = source;
    this.filteredFilghtsBySource=this.filteredFlights;
    if(!this.source)
      this.filteredFlights = this.filteredFlightsByDestination;
    this.resetSourceDestinationEmpty();
  }

  filterByDestination(destination:any){
    this.filteredFlights = (destination) ? 
                            this.filteredFlights.filter((p:any)=>p.destination.toLowerCase().includes(destination.toLowerCase()))
                            :this.filteredFlights;
    this.filteredFlightsByDestination = this.filteredFlights;
    this.destination = destination;
    if(!this.destination)
      this.filteredFlights = this.filteredFilghtsBySource;
    this.resetSourceDestinationEmpty();
  }

  resetSourceDestinationEmpty(){
    if(!this.source && !this.destination)
      this.filteredFlights = this.flights;
  }

  // '/booking/'+flight.flightId
  navigateToBooking(id: any) {
    console.log("ID : " + id);
    this.router.navigate(['booking/'+id])
    .then(() => {
      window.location.reload();
    });
  }

  getFlightsByQuery() {
    this.query.flightSource = this.sourceInp;
    this.query.flightDestination = this.destinationInp;
    this.query.flightDepartureDate = this.startDateInp;
    this.query.flightArrivalDate = this.endDateInp;

      this.flightService.getFlightsByQueryService(this.query).subscribe((flights: Flight[])=> {
            this.filteredFlights=flights; 
          });
  }

}
