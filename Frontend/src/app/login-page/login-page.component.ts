import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../model/User';
import {Credential} from '../model/Credential';
import { AuthService } from '../services/AuthService';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '../services/alertService';


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {


  user: User;
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  form: FormGroup

  constructor(private auth: AuthService, private router: Router, private fb: FormBuilder, private route: ActivatedRoute, private alertservice: AlertService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: this.email,
      password: this.password
    })
  }
  	
  signIn() {
    if(this.form.valid) {

      let payload = <Credential> {
        email: this.email.value,
        password: this.password.value
      }

      this.auth.authenticateUser(payload).subscribe((response: any) => {
        
        //cache the response token
        localStorage.setItem("token", response.token);
        this.auth.token.next(response.token)
        console.log(response.token);

        let user: User;
        this.auth.getUser(payload.email).subscribe((data: User) => {
          //get the user data and cache it
          user = {...data};
          this.auth.user.next(user);
          localStorage.setItem('user', JSON.stringify(user));


          //navigate to the redirect URL if necessary
          let redirectURL = "";
          let params = this.route.snapshot.queryParams;
          if (params['redirectURL']) {
            redirectURL = params['redirectURL'];
          }

          if (redirectURL != "") this.router.navigateByUrl(redirectURL).catch(() => this.router.navigate(['app']));
          else this.router.navigate(['app'])
          
        });
        
      },
      (error) => {
        this.alertservice.error(error.error);
      });
    }
  }

  getErrorMessage() {
    if (this.email.hasError('required') || this.password.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt!"
    }

    if (this.email.hasError('email')) return "Das ist eine ungültige Email"

  }

}
