import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

const httpOptions =  {
    headers: new HttpHeaders({
       'Content-Type': 'application/json'
    })
 };
 
 const relationServiceAPI: String = "http://localhost:8081/" 
 
 @Injectable({ providedIn: 'root' })
 export class FollowerService
  {

    followers = new BehaviorSubject<number[]>([]);

    following = new BehaviorSubject<number[]>([]);
 
     constructor(private http: HttpClient) {}
 

     getFollowers(userid: number): Promise<number[]> {
         return this.http.get<number[]>(relationServiceAPI+"users/followdby/"+userid).toPromise();
     }

     getFollowing(userid: number): Promise<number[]> {
        return this.http.get<number[]>(relationServiceAPI+"users/followds/"+userid).toPromise();
     }


     async setFollowersObservable(userid: number) {
        let ids: number[] = await this.getFollowers(userid);
        console.log(ids)
     }








  



}


