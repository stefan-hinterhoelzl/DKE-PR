import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/AuthService';
import { User } from '../model/User';
import { ObjectUnsubscribedError } from 'rxjs';
import { UseExistingWebDriver } from 'protractor/built/driverProviders';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  Colors: any = ['Blau', 'Rot', 'Gelb', 'Lila', 'Grün', 'Pink'];

  errorMessage: String;
  error: boolean;
  form: FormGroup;
  success: boolean;
  

  email = new FormControl('', [Validators.required, Validators.email]);
  firstname = new FormControl('', [Validators.required]);
  lastname = new FormControl('', [Validators.required]);
  phonenumber = new FormControl('');
  password = new FormControl('', [Validators.required]);
  // color = new FormControl('');

  constructor(private apiservice: AuthService, private router: Router, private fb: FormBuilder) { }

  ngOnInit() {
    this.form = this.fb.group({
      email: this.email,
      firstname: this.firstname,
      lastname: this.lastname,
      phonenumber: this.phonenumber,
      password: this.password
    })
  }


  signUpUser() {

    if(this.form.valid) {

      const user = <User> {
        //color: this.color.value;
        email: this.email.value,
        firstname: this.firstname.value,
        lastname: this.lastname.value,
        password: this.password.value,
        phonenumber: this.phonenumber.value
      }
      
      var addedUser: User;
      this.apiservice.postUser(user).subscribe((data: User) => {
        addedUser = {...data}
        this.router.navigate(['app']);
      },
      (error) => {
        this.errorMessage = error.error;
        this.error = true;
        setTimeout(() => this.error = false, 3500);
      });
    }
  }

  getErrorMessage() {
    if (this.email.hasError('required') || this.firstname.hasError('required') || this.lastname.hasError('required') || this.password.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt!"
    }

    if (this.email.hasError('email')) return "Das ist eine ungültige Email"

  }

}