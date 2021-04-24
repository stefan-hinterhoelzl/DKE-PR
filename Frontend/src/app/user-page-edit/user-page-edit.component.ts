import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { notEqual, notStrictEqual } from 'node:assert';
import { MyErrorStateMatcher } from '../helpers/MyErrorStateMatcher';
import { PasswordChangeCredential } from '../model/PasswordChangeCredential';
import { User } from '../model/User';
import { AlertService } from '../services/alertService';
import { AuthService } from '../services/AuthService';

@Component({
  selector: 'app-user-page-edit',
  templateUrl: './user-page-edit.component.html',
  styleUrls: ['./user-page-edit.component.css']
})
export class UserPageEditComponent implements OnInit {

  routeUserID: string; 
  dataform: FormGroup;
  pwform: FormGroup;
  matcher: MyErrorStateMatcher = new MyErrorStateMatcher();
  user: User;
  
  firstname = new FormControl('', [Validators.required]);
  lastname = new FormControl('', [Validators.required]);
  phonenumber = new FormControl('');

  oldpw = new FormControl('', [Validators.required]);
  newpw = new FormControl('', [Validators.required]);
  newpwconfirm = new FormControl('');

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private router: Router, private auth: AuthService, private alertservice: AlertService) { }

  ngOnInit(): void {

    this.route.parent.params.subscribe(params => {
      this.routeUserID = params['id'];
      this.user = JSON.parse(localStorage.getItem('user'))
      if (this.user.id.toString() != this.routeUserID) {
        this.router.navigate(['/user/'+this.routeUserID+"/posts"])
      }
      
    });

    this.dataform = this.fb.group({
      firstname: this.firstname,
      lastname: this.lastname,
      phonenumber: this.phonenumber,
    });

    this.pwform = this.fb.group({
      oldpw: this.oldpw,
      newpw: this.newpw,
      newpwconfirm: this.newpwconfirm    
    },
    {
      validator: [this.checkSamePasswords]
      
    }   
    );

    
  }


  updateUserData() {

    let changes = <User> {
      firstname:  this.firstname.value,
      lastname:  this.lastname.value,
      phonenumber:  this.phonenumber.value,
      pokemonid: this.user.pokemonid,
    }   

    //save to database
    this.auth.token.next("");
    this.auth.updateUser(changes, this.user.id).subscribe((data: User) => {
      //on success, cache the data and change the observable
      this.user = data;
      localStorage.setItem('user', JSON.stringify(data));
      this.auth.user.next(data);


      this.dataform.reset();

      Object.keys(this.dataform.controls).forEach(key => {
      this.dataform.get(key).setErrors(null);
      this.alertservice.success("Benutzer "+data.email+ " wurde aktualsiert.");
    });
    },
    (error) => {
      this.alertservice.error(error.error);
      }
    );

    
  }

  updateUserPassword() {
    let payload: PasswordChangeCredential = {
      oldpassword: this.oldpw.value,
      newpassword: this.newpw.value,
      newpasswordconfirm: this.newpwconfirm.value,
    }
    this.auth.updateUserPassword(payload, this.user.id).subscribe((data: User) => {
      this.dataform.reset();

      Object.keys(this.dataform.controls).forEach(key => {
      this.dataform.get(key).setErrors(null);

      this.alertservice.success("Passwort wurde aktualisiert. Sie werden abgemeldet");
      setTimeout(() => this.auth.logout(),3000);

    });
    },
    (error) => {
      this.alertservice.error(error.error);
    }
    );
  }

  getErrorMessage() {
    if (this.firstname.hasError('required') || this.lastname.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt!"
    }
  }

  checkSamePasswords(group: FormGroup) {
    let pass = group.controls.newpw.value;
    let confirmPass = group.controls.newpwconfirm.value;
    return pass === confirmPass ? null : { notSame: true };
  }

}
