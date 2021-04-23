import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ActionSequence } from 'selenium-webdriver';
import { User } from '../model/User';

@Component({
  selector: 'app-user-page-edit',
  templateUrl: './user-page-edit.component.html',
  styleUrls: ['./user-page-edit.component.css']
})
export class UserPageEditComponent implements OnInit {

  routeUserID: string; 
  dataform: FormGroup;
  errorMessage: String;
  successMessage: String;
  error: boolean;
  success: boolean;

  user: User;
  
  firstname = new FormControl('', [Validators.required]);
  lastname = new FormControl('', [Validators.required]);
  phonenumber = new FormControl('');

  password = new FormControl('', [Validators.required]);
  newpassword = new FormControl('', [Validators.required]);
  newpasswordrepeat = new FormControl('', [Validators.required]);

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private router: Router) { }

  ngOnInit(): void {

    this.route.parent.params.subscribe(params => {
      this.routeUserID = params['id'];
      this.user = JSON.parse(localStorage.getItem('user'))
      if (this.user.id.toString() != this.routeUserID) {
        this.router.navigate(['user/'+this.user.id+"posts"])
      }
      
    });

    this.dataform = this.fb.group({
      firstname: this.firstname,
      lastname: this.lastname,
      phonenumber: this.phonenumber,
    });

    
  }


  updateUser() {
    console.log("updated");
  }

  getErrorMessage() {
    if (this.firstname.hasError('required') || this.lastname.hasError('required') || this.password.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt!"
    }
  }

}