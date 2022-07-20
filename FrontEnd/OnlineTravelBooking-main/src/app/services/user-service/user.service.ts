import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Bookingdata } from 'src/app/data/bookingdata';
import baseURL from '../baseURL';


import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public emailSubject = new BehaviorSubject("Hello");
  currentEmail = this.emailSubject.asObservable();

  sendEmail(data: any) {
    this.emailSubject.next(data);
  }

  public tokenObject = new BehaviorSubject("token");

  sendToken(data: any) {
    this.tokenObject.next(data);
  }

  result: any;
  constructor(private http: HttpClient) {

  }
  public addUser(user: any) {
    console.log(user.userName);
    this.result = this.http.post(`${baseURL}/users/register`, user);
    console.log("This is Response " + this.result);
    if (!this.result) {
      console.log("Object is empty")
    }
    return this.result;
  }

  getUserByEmail(email: any) {
    return this.http.get(baseURL+"/users/get/"+email);
  }

  getBookingsByUserId(id: any): Observable<Bookingdata[]>{
    return this.http.get<Bookingdata[]>(baseURL+"/flight-booking/get/"+id);
  }

  cancelBooking(id: any) {
      console.log("Service get's called....");
      return  this.http.delete(baseURL+"/flight-booking/delete/" + id);
  }
  passwordReset(email: any) : Observable<any>{
    return this.http.post<any>(baseURL+"/users/resetPassword", email);
  }
  changePassword(token:any, data: any): Observable<any> {
    return this.http.post<any>(token, data);
  }

  getEmail() {
    console.log(this.emailSubject.asObservable.toString());
    return this.emailSubject.asObservable;
  }
}
   