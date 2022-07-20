import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder,FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service/user.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html',
  styleUrls: ['./changepassword.component.css']
})
export class ChangepasswordComponent implements OnInit {

  reactiveForm: FormGroup;
  email : any;
  token: any;
  public data = {
    userEmail: '',
    newPassword: '',
  }
  constructor(private formBuilder: FormBuilder, private service: UserService, private router :Router) {
    this.reactiveForm = this.formBuilder.group({
      userPassword: new FormControl(null, [Validators.required, Validators.minLength(8)]),
      matchingPassword: new FormControl(null, [Validators.required]),
    }, {
      validators: this.mustMatch('userPassword', 'matchingPassword')
    })
   }
  get f() {
    return this.reactiveForm.controls;   
  }
  mustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (matchingControl.errors && !matchingControl.errors['mustMatch']) {
        return;
      }
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ mustMatch: true })
      } else {
        matchingControl.setErrors(null);
      }
    }
  }

  ngOnInit(): void {
    this.service.currentEmail.subscribe(mail => this.email = mail)
    this.service.tokenObject.asObservable().subscribe(data => this.token = data);
  }

  onFormSubmit() {
    if (this.reactiveForm.invalid) {
      console.log("Invalid");
      return;
    }
    console.log("Gettting called inside...")
    this.data.newPassword = this.reactiveForm.value.matchingPassword;
    this.data.userEmail = this.email;
    console.log(this.token);
    console.log(this.email)
    console.log(this.data);
    this.service.changePassword(this.token, this.data).subscribe(
      (result) => {
        Swal.fire(result.data);
        this.router.navigate(['/login']);
      }
    );
  }
}
