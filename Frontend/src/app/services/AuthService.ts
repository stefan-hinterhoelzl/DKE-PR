import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, throwError } from "rxjs";
import { catchError, retry } from 'rxjs/operators';
import {User} from "src/app/model/User";
import {Credential} from "src/app/model/Credential";
import { Router } from "@angular/router";

const httpOptions =  {
   headers: new HttpHeaders({
      'Content-Type': 'application/json'
   })
};

@Injectable({ providedIn: 'root' })
export class AuthService
 {
    

  user = new BehaviorSubject<User>(null);



    constructor(private http: HttpClient, private router: Router) {}

    userServiceAPIURL: String = "http://localhost:8080/"

    //UserService API
    getUser(email: String) {
      return this.http.get<User>(this.userServiceAPIURL+"user/"+email)
      .pipe();
    }

    postUser(user: User): Observable<User> {
      return this.http.post<User>(this.userServiceAPIURL+"users", user, httpOptions)
      .pipe();
    }

    authenticateUser(payload: Credential): Observable<String> {
      return this.http.post<String>(this.userServiceAPIURL+"authenticate", payload, httpOptions)
      .pipe()
    }

    autoLogin() {
      const user: User = JSON.parse(localStorage.getItem('user'));
      if(!user) {
        this.user.next(null);
        return;
      }

      this.user.next(user);

    }

    logout() {
      localStorage.removeItem('user');
      this.user.next(null);
      this.router.navigate(['login']);
    }



    
 }