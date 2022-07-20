import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseURL from '../baseURL';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  add(flight:any){
    return this.http.post(baseURL+"/flights/register", flight);
  }

  delete(flightId:any): Observable<any> {
    return this.http.delete(baseURL+"/flights/delete/"+flightId);
  }

  update(flightId:any, flight:any){
    return this.http.put(baseURL+"/flights/update/"+flightId, flight);
  }

}
