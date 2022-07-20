import { FlightService } from './services/flight-service/flight.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BsNavbarComponent } from './bs-navbar/bs-navbar.component';
import { HomeComponent } from './home/home.component';
import { CustomFormsModule } from 'ng2-validation';

// custom added
import { RouterModule } from '@angular/router';
import {NgbPaginationModule, NgbAlertModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';


import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DataTablesModule } from "angular-datatables";
import { FlightComponent } from './travel/flight/flight.component';
import { HotelComponent } from './travel/hotel/hotel.component';
import { TrainComponent } from './travel/train/train.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Angular Material
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
//  

import { MatExpansionModule } from '@angular/material/expansion';
import { MatCardModule } from '@angular/material/card';
import { RegistrationComponent } from './user/registration/registration.component';
import { LoginComponent } from './user/login/login.component';
import { BookingComponent } from './travel/booking/booking.component';
import { ProfileComponent } from './user/profile/profile.component';
import { UserbookingComponent } from './user/userbooking/userbooking.component';
import { PasswordresetComponent } from './user/passwordreset/passwordreset.component';
import { ChangepasswordComponent } from './user/changepassword/changepassword.component';
import { UserService } from './services/user-service/user.service';
import { AdminFlightComponent } from './admin/admin-flight/admin-flight.component';
import { AdminService } from './services/admin-service/admin.service';
import { FlightFormComponent } from './admin/flightform/flightform.component';
import { UserGuard } from './guards/user.guard';
import { AdminGuard } from './guards/admin.guard';
import {MatSelectModule} from '@angular/material/select';
import { UpdateFlightComponent } from './admin/update-flight/update-flight.component';
import { LoginService } from './services/login-service/login.service';
// import { RegistrationComponent } from './registration/registration.component';

@NgModule({
  declarations: [
    AppComponent,
    BsNavbarComponent,
    HomeComponent,
    LoginComponent,
    FlightComponent,
    HotelComponent,
    TrainComponent,
    RegistrationComponent,
    BookingComponent,
    ProfileComponent,
    UserbookingComponent,
    PasswordresetComponent,
    ChangepasswordComponent,
    AdminFlightComponent,
    FlightFormComponent,
    UpdateFlightComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    DataTablesModule,
    RouterModule,
    HttpClientModule,
    // for form validation 
    CustomFormsModule,
    // Angular Materials 
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatExpansionModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatSelectModule,
    // NgbModule.forRoot(),
    RouterModule.forRoot([
      // routes for normal users
      {path:'', component:HomeComponent},
      {path:'login', component:LoginComponent},
      {path:'register', component:RegistrationComponent},


      //route from home
      {path:'travel/flight', component:FlightComponent},
      {path:'travel/train', component:TrainComponent},
      {path:'travel/hotel', component:HotelComponent},
      {path:'booking/:flightId', component:BookingComponent},
      {path:'users/profile', component:ProfileComponent},
      {path:'users/flight/booking', component:UserbookingComponent},
      {path: 'users/forgot-password', component:PasswordresetComponent},
      {path: 'users/change-password', component:ChangepasswordComponent},
      {path:'admin/flight', component:AdminFlightComponent},
      {path:'admin/flight/add', component:FlightFormComponent},
      {path:'admin/flight/update', component:UpdateFlightComponent},


 
    ]),
    BrowserAnimationsModule
  ],
  providers: [
    FlightService,
    UserService,
    AdminService,
    UserGuard,
    AdminGuard,
    LoginService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
