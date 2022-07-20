import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user-service/user.service';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  isAdmin: any;
  user: any;
  constructor(private userService: UserService) { 
    if (localStorage.getItem("isAdmin") === "true") {
      this.isAdmin = "admin"
    } else {
      this.isAdmin = "user"
    }
    this.userService.getUserByEmail(localStorage.getItem("userEmail")).subscribe((data) => this.user = data);
   }

  ngOnInit(): void {
  }

}
