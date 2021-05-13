import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, ObservedValueOf, throwError } from "rxjs";
import { catchError, retry, take } from 'rxjs/operators';
import {User} from "src/app/model/User";
import {Credential} from "src/app/model/Credential";
import { Router } from "@angular/router";
import { Posting } from "../model/Posting";

const httpOptions =  {
   headers: new HttpHeaders({
      'Content-Type': 'application/json'
   })
};

const userServicePOSTAPIURL: String = "http://localhost:8087/" 
const userServiceGETAPIURL: String = "http://localhost:8082/"

@Injectable({ providedIn: 'root' })
export class PostService
 {

   posts= new BehaviorSubject<Posting[]>([]);

    constructor(private http: HttpClient) {}


    savePost(post: Posting): Observable<Posting> {
        return this.http.post<Posting>(userServicePOSTAPIURL+"posts", post, httpOptions).pipe(take(1));
    }

    getAllUserPosts(userid: String): Observable<Posting[]> {
       return this.http.get<Posting[]>(userServiceGETAPIURL+"posts/"+userid, httpOptions).pipe(take(1));
    }

    public get userPosts(): Posting[] {
       return this.posts.value;
    }

    //used to cache the users own posting
    public async setPostObservable(userid: String){
      this.posts.next(await this.getAllUserPosts(userid).toPromise())
    }

 }