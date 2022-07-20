 import { Component, OnInit } from '@angular/core'; 
 import { MatSnackBar } from '@angular/material/snack-bar';
 import { Router } from '@angular/router';
 import { LoginService } from 'src/app/services/login-service/login.service';
 import { user } from 'src/app/data/user';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  // loggedData: BsNavbarComponent;

  userLoginData = {
    userEmail: '',
    userPassword: ''
  }

  constructor(private snack: MatSnackBar, private loginService: LoginService, private router: Router) {
    // this.router.navigateByUrl('/BsNavbarComponent', { skipLocationChange: true }).then(() => {
    //   this.router.navigate(['/']);
  // });
   }

  ngOnInit(): void {
  }

  loginFormSubmit() {
    if (this.userLoginData.userEmail.trim() == '' || this.userLoginData.userEmail == null) {
      this.snack.open("Email is required", 'ok', {
        duration: 3000
      });
      return;
    }

    if (this.userLoginData.userEmail.trim() == '' || this.userLoginData.userEmail == null) {
      this.snack.open("Password is required", 'ok', {
        duration: 3000
      });
      return;
    }

    this.loginService.loginMethod(this.userLoginData).subscribe(
      (data: any) => {
        console.log(data);
        console.log(JSON.stringify(data));
        if (data === null) {
          this.snack.open('Invalid Details ', 'ok', {
            duration: 3000
          })
        } else {
          console.log(data.userEmail);
          console.log(data)
          localStorage.setItem("userEmail", data.userEmail);
          localStorage.setItem("userName", data.userName);
          localStorage.setItem("userId", data.userId);
          localStorage.setItem("isAdmin", data.isAdmin);
          localStorage.setItem("isEnabled", data.enabled);
          this.router.navigate(['/'])
            .then(() => {
              window.location.reload();
            });          
        }
        }

      ,
      (error) => {
        this.snack.open('Invalid Details ', 'ok', {
          duration: 3000
        })
      }
      )
   
  }

  passwordReset() {
    this.router.navigate(['/users/forgot-password'])
    .then(() => {
      window.location.reload();
    });
  }

}
