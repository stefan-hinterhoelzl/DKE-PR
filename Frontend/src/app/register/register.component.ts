import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/AuthService';
import { User } from '../model/User';
import { ObjectUnsubscribedError } from 'rxjs';
import { UseExistingWebDriver } from 'protractor/built/driverProviders';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { positionElements } from 'ngx-bootstrap/positioning';
import { MyErrorStateMatcher } from '../helpers/MyErrorStateMatcher';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  Colors: any = ['Blau', 'Rot', 'Gelb', 'Lila', 'Grün', 'Pink'];

  errorMessage: String;
  successMessage: String;
  error: boolean;
  form: FormGroup;
  success: boolean;
  matcher: MyErrorStateMatcher = new MyErrorStateMatcher();
  email: FormControl;
  firstname: FormControl;
  lastname: FormControl;
  phonenumber: FormControl;
  password: FormControl;
  passwordconfirm: FormControl;

  
  // color = new FormControl('');

  constructor(private apiservice: AuthService, private router: Router, private fb: FormBuilder) {
    this.email = new FormControl('', [Validators.required, Validators.email]);
    this.firstname = new FormControl('', [Validators.required]);
    this.lastname = new FormControl('', [Validators.required]);
    this.phonenumber = new FormControl('');
    this.password = new FormControl('', [Validators.required]);
    this.passwordconfirm = new FormControl('');

    this.form = this.fb.group({
      email: this.email,
      firstname: this.firstname,
      lastname: this.lastname,
      phonenumber: this.phonenumber,
      password: this.password,
      passwordconfirm: this.passwordconfirm
    }, 
    {validator: this.checkPasswords}    
    );

   }

  ngOnInit() {

  
    
  }


  signUpUser() {
    let pokemonid: String = (Math.floor(Math.random() * 494)+1).toString();

    const user = <User> {
      email: this.email.value,
      firstname: this.firstname.value,
      lastname: this.lastname.value,
      password: this.password.value,
      phonenumber: this.phonenumber.value,
      pokemonid: pokemonid
    }
    
    var addedUser: User;
    this.apiservice.postUser(user).subscribe((data: User) => {
      addedUser = {...data}
      this.successMessage = "User "+addedUser.email+" wurde angelegt.";
      this.success = true;
      setTimeout(() => this.success = false, 3500);
      
    },
    (error) => {
      this.errorMessage = error.error;
      this.error = true;
      setTimeout(() => this.error = false, 3500);
    });
    
    this.form.reset();

    Object.keys(this.form.controls).forEach(key => {
      this.form.get(key).setErrors(null);
    });

    // this.form.setValue({
    //   email: "",
    //   firstname: "",
    //   lastname: "",
    //   password: "",
    //   phonenumber: "",
    // });
  
  }

  getErrorMessage() {
    if (this.email.hasError('required') || this.firstname.hasError('required') || this.lastname.hasError('required') || this.password.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt"
    }

    if (this.email.hasError('email')) return "Das ist eine ungültige Email"
  }

  checkPasswords(group: FormGroup) {
    let pass = group.controls.password.value;
    let confirmPass = group.controls.passwordconfirm.value;
    return pass === confirmPass ? null : { notSame: true };
  }
  



}