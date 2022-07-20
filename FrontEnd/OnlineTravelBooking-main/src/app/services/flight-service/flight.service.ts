// import { Flight } from './entity/flight';
import {HttpClient} from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Flight } from 'src/app/data/flight';
import baseURL from '../baseURL';
import { data } from 'jquery';

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  private url:string = '/assets/data/indigo.json';

  id = new BehaviorSubject(1);

  sendId(id: any) {
    this.id.next(id);
  }
  constructor(private http: HttpClient) { }

  create(category:any){
    // add category in Database;
  }
  
  getAll(): Observable<Flight[]>{
    console.log("Calling...");
    // return this.http.get<Flight[]>(this.baseURL+"flights.json");
    return this.http.get<Flight[]>(baseURL+ "/flights/all");
  }

  get(FlightId:any){
    // this is of type Observable so we have to convert this in the caller method to _____ ?
    return this.http.get(this.url);
  }

  getFlightById(flightId:any) : Observable<Flight>{
   return this.http.get<Flight>(baseURL +"/flights/get/"+flightId);
  }

  // update(FlightId:any, Flight:any){
  //   // write here code to call database and 
  //   // update the Flight whose id == FlightId with 
  //   // and data is in Flight 

  //   // (optional): you can also return the promise from here back to the caller 
  // }

  // delete(FlightId:any){
  //   // delete Flight with id == FlightId
  // }

  getFlightsByQueryService(query: any): Observable<Flight[]> {
          // "flightSource" : source ,
      // "flighgtDestination": dest,
      // "flightDepartureDate": start,
      // "flightArrivalDate": end
      // "flightDepartureDate": "2022-10-12",
      // "flightArrivalDate":"2022-10-12"
    
    return this.http.post<Flight[]>(baseURL+'/flights/get/query', query);
  }
  // new added
  add(flight:any){
    console.log("flight service add invoked")
    return this.http.post(baseURL+"/flights/register", flight);
  }

  delete(flightId:any): Observable<any> {
    return this.http.delete(baseURL+"/flights/delete/"+flightId);
  }

  update(flightId:any, flight:any){
    return this.http.put(baseURL+"/flights/update/"+flightId, flight);
  }
}




// --------------


 