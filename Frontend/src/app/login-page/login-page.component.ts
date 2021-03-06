import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../model/User';
import {Credential} from '../model/Credential';
import { AuthService } from '../services/AuthService';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from '../services/alertService';
import { PostService } from '../services/postservice';
import { FollowerService } from '../services/followerservice';
import { NotificationService } from '../services/notificationService';


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

  constructor(private auth: AuthService, private router: Router, private fb: FormBuilder, private route: ActivatedRoute, private alertservice: AlertService, private ps: PostService, private fs: FollowerService, private ns: NotificationService) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: this.email,
      password: this.password
    })
  }
  	
  async signIn() {
    if(this.form.valid) {

      let email: String = this.email.value;
      email = email.toLowerCase();

      let payload = <Credential> {
        email: email,
        password: this.password.value
      }

      await this.auth.authenticateUser(payload).then(async (response: any) => {
        
        //cache the response token
        localStorage.setItem("token", response.token);
        this.auth.token.next(response.token)
        console.log(response.token);

        let user: User;
        await this.auth.getUser(payload.email).then((data: User) => {
          //get the user data and cache it
          user = {...data};
          this.auth.user.next(user);
          localStorage.setItem('user', JSON.stringify(user));

          //cach the user postings
          this.ps.setPostObservable(user.id);

          this.ps.setFeedObservable(user.id);


          //cache the FollowersObservable
          this.fs.setFollowersObservable(user.id);

          //cache the FollowingObservable
          this.fs.setFollowingObservable(user.id);

          //cache the NotsObservable
          this.ns.setNotsObservable(user.id);


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
      return "Nicht alle Pflichtfelder wurden ausgef??llt!"
    }

    if (this.email.hasError('email')) return "Das ist eine ung??ltige Email"

  }

}
