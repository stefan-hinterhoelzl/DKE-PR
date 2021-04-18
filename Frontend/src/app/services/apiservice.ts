import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError, retry } from 'rxjs/operators';
import {User} from "src/app/model/User";
import {Credential} from "src/app/model/Credential";

const httpOptions =  {
   headers: new HttpHeaders({
      'Content-Type': 'application/json'
   })
};

@Injectable({ providedIn: 'root' })
export class APIService
 {
    



    constructor(private http: HttpClient) {}

    userServiceAPIURL: String = "http://localhost:8080/"



    //UserService API
    getUser() {
      return this.http.get<User>(this.userServiceAPIURL+"user/email1@gmail.com")
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

    
 }