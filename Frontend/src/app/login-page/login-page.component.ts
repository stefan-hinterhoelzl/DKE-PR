import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { User } from '../model/User';
import {Credential} from '../model/Credential';
import { APIService } from '../services/apiservice';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {


  user: User;
  errorMessage: String;

  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);

  constructor(private apiservice: APIService, private router: Router) { }

  ngOnInit(): void {
  }
  	




  signIn() {
    let payload = <Credential> {
      email: this.email.value,
      password: this.password.value
    }

    this.apiservice.authenticateUser(payload).subscribe((data) => {
      console.log(data)
      localStorage.setItem("loggedIn", "True");
      this.router.navigate(['#']);
      
    },
    (error) => {
      
      this.errorMessage = error;
      console.log(error)
    });
  }

  // showUser() {
  //   this.apiservice.getUser().subscribe((data: User) =>  {
  //     this.user = {...data}
  //     console.log(this.user)
  //   });
    
  //}
  

  getErrorMessage() {
    if (this.email.hasError('required') || this.password.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt!"
    }

    if (this.email.hasError('email')) return "Das ist eine ungültige Email"

  }

}
