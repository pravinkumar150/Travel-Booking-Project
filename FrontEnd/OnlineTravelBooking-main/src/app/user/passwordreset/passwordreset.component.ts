import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service/user.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-passwordreset',
  templateUrl: './passwordreset.component.html',
  styleUrls: ['./passwordreset.component.css']
})
export class PasswordresetComponent implements OnInit {

  dataObject = {userEmail: ''}

  message: any;
  constructor(private userService: UserService, private router: Router) {

   }

  ngOnInit(): void {
    this.userService.currentEmail.subscribe(msg => this.message = msg);
  }   
  
  submit(email: any){
    this.userService.sendEmail(email.value); // sending data to the service to update the currentEmail.
    this.dataObject.userEmail = email.value;
    this.userService.passwordReset(this.dataObject).subscribe(
      (url) =>{
        console.log(url.data);
        if (url.data == "invalid email") {
         Swal.fire("You don't have any account with this email..")
        } else {
          this.userService.sendToken(url.data);
          this.router.navigate(['/users/change-password']);
        }
      }
    );
  }

}
