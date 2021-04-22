import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, ObservedValueOf, throwError } from "rxjs";
import { catchError, retry, take } from 'rxjs/operators';
import {User} from "src/app/model/User";
import {Credential} from "src/app/model/Credential";
import { Router } from "@angular/router";

const httpOptions =  {
   headers: new HttpHeaders({
      'Content-Type': 'application/json'
   })
};

const userServiceAPIURL: String = "http://localhost:8080/" 

@Injectable({ providedIn: 'root' })
export class AuthService
 {
    
  

  user = new BehaviorSubject<User>(null);



    constructor(private http: HttpClient, private router: Router) {}

    
    //UserService API
    getUser(email: String) {
      return this.http.get<User>(userServiceAPIURL+"user/"+email).pipe(take(1));
    }

    getUserPerID(id: number) {
      return this.http.get<User>(userServiceAPIURL+"userPerID/"+id).pipe(take(1));
    }

    postUser(user: User): Observable<User> {
      return this.http.post<User>(userServiceAPIURL+"users", user, httpOptions).pipe(take(1));
    }

    authenticateUser(payload: Credential): Observable<String> {
      return this.http.post<String>(userServiceAPIURL+"authenticate", payload, httpOptions).pipe(take(1));
    }

    updateUser(user: User, id: number): Observable<User> {
      return this.http.put<User>(userServiceAPIURL+"user/"+id, user, httpOptions).pipe(take(1));
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
      localStorage.removeItem('token');
      this.user.next(null);
      this.router.navigate(['login']);
    }



    
 }