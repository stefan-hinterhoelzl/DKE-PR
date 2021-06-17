import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, ObservedValueOf, throwError } from "rxjs";
import { catchError, retry, take } from 'rxjs/operators';
import {User} from "src/app/model/User";
import {Credential} from "src/app/model/Credential";
import { Router } from "@angular/router";
import { Posting } from "../model/Posting";
import { ObserversModule } from "@angular/cdk/observers";
import { AlertService } from "./alertService";

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

   posts = new BehaviorSubject<Posting[]>([]);

   feed = new BehaviorSubject<Posting[]>([]);

    constructor(private http: HttpClient) {}


    savePost(post: Posting): Promise<Posting> {
        return this.http.post<Posting>(userServicePOSTAPIURL+"post", post, httpOptions).toPromise();
    }

    getAllUserPosts(userid: number): Promise<Posting[]> {
       return this.http.get<Posting[]>(userServiceGETAPIURL+"posts/"+userid).toPromise();
    }

    deletePost(postid: string): Promise<any> {
       return this.http.delete(userServicePOSTAPIURL+"deletepost/"+postid).toPromise();
    }

    deleteAllByAuthor(authorid: number): Promise<any> {
      return this.http.delete(userServicePOSTAPIURL+"deleteAll/"+authorid).toPromise();
    }

    getAllPosts(): Promise<Posting[]> {
      return this.http.get<Posting[]>(userServiceGETAPIURL+"posts").toPromise();
    }

    public get userPosts(): Posting[] {
       return this.posts.value;
    }

    public get feedPosts(): Posting[] {
       return this.feed.value;
    }

    //used to cache the users own posting
    public async setPostObservable(userid: number){
      let posts: Posting[] = await this.getAllUserPosts(userid);
      if(posts != undefined) {
         this.posts.next(posts);
      }
    }

    public async setFeedObservable(userid: number){
       let posts: Posting[] = await this.getAllPosts();
       if(posts != undefined) {
         posts = posts.filter(curr => curr.authorid !== userid);
         this.feed.next(posts);
       }
    }

 }