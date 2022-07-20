import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login-service/login.service';
import { UserService } from '../services/user-service/user.service';

@Component({
  selector: 'app-bs-navbar',
  templateUrl: './bs-navbar.component.html',
  styleUrls: ['./bs-navbar.component.css']
})
export class BsNavbarComponent implements OnInit {
  public loggedUser: any;
  public isAdmin :any;
  redirectToUrl: any;
  constructor(private router: Router, private userService: UserService, private loginService: LoginService) { 
    if (localStorage.getItem("userName") === null){
      this.loggedUser = "Anonymous User";
    } else {
      this.loggedUser = localStorage.getItem("userName");
      if (localStorage.getItem("isAdmin") === "true") {
        this.isAdmin = "admin"
      } else {
        this.isAdmin = "user"
      }
    }
  }

  ngOnInit(): void {}

  redirectTo() {
    if (localStorage.getItem("userEmail") === null) {
      this.router.navigate(['/login']);
    } else {
      this.router.navigate(['/travel/flight']);
    }
  }
  logoutAndRemoveCache() {
    localStorage.clear();
    this.router.navigate([''])
          .then(() => {
            window.location.reload();
          });
  }
    
}
