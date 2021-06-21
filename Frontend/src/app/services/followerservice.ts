import { HttpClient, HttpHeaders } from "@angular/common/http";
import { ProviderMeta } from "@angular/compiler";
import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { User } from "../model/User";
import { NotificationService } from "./notificationService";

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
 
     constructor(private http: HttpClient, private ns: NotificationService) {}
 

     getFollowers(userid: number): Promise<number[]> {
         return this.http.get<number[]>(relationServiceAPI+"users/followedby/"+userid).toPromise();
     }

     getFollowing(userid: number): Promise<number[]> {
        return this.http.get<number[]>(relationServiceAPI+"users/follows/"+userid).toPromise();
     }

     followUser(curruser: User, followuser: User): Promise<void> {
        let res: Promise<void> = this.http.post<any>(relationServiceAPI+"users/follows/"+curruser.id+"/"+followuser.id, null, httpOptions).toPromise();
         this.ns.saveUserNotification(curruser, followuser)
         return res;

     }

     UnfollowUser(curruser: number, followuser: number): Promise<void> {
      return this.http.delete<any>(relationServiceAPI+"users/follows/"+curruser+"/"+followuser).toPromise();
   }


     async setFollowersObservable(userid: number) {
       
      let ids: any[] = await this.getFollowers(userid);
      let numbers: number[] = []

      if (ids != undefined) {
        
         ids.forEach((element) => {
            numbers.push(element.id);
         });

         this.followers.next(numbers);
      }
     }

     async setFollowingObservable(userid: number) {
        let ids: any[] = await this.getFollowing(userid);
        let numbers: number[] = []

        if (ids != undefined) {
        
         ids.forEach((element) => {
            numbers.push(element.id);
         });
         this.following.next(numbers);
      }
     }










  



}


