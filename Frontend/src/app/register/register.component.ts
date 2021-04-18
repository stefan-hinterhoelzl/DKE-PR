import { Component, OnInit } from '@angular/core';
import { APIService } from '../services/apiservice';
import { User } from '../model/User';
import { ObjectUnsubscribedError } from 'rxjs';
import { UseExistingWebDriver } from 'protractor/built/driverProviders';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  Colors: any = ['Blau', 'Rot', 'Gelb', 'Lila', 'Grün', 'Pink'];

  email = new FormControl('', [Validators.required, Validators.email]);
  firstname = new FormControl('', [Validators.required]);
  lastname = new FormControl('', [Validators.required]);
  phonenumber = new FormControl('');
  password = new FormControl('', [Validators.required]);
  // color = new FormControl('');

  constructor(private apiservice: APIService) { }

  ngOnInit() {
  }


  signUpUser() {
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
      console.log(addedUser)
    });
  }

  getErrorMessage() {
    if (this.email.hasError('required') || this.firstname.hasError('required') || this.lastname.hasError('required') || this.password.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt!"
    }

    if (this.email.hasError('email')) return "Das ist eine ungültige Email"

  }

}