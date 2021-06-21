import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import {Notification} from "../model/Notification";
import * as uuid from 'uuid';
import { User } from "../model/User";


const notificationServiceAPIURL: String = "http://localhost:8088/"

const httpOptions =  {
    headers: new HttpHeaders({
       'Content-Type': 'application/json'
    })
 };



@Injectable({ providedIn: 'root' })
export class NotificationService
 {

    nots  = new BehaviorSubject<Notification[]>([]);

    constructor(private http: HttpClient) {}


    getAllUserNotifications(userid: number): Promise<Notification[]> {
        return this.http.get<Notification[]>(notificationServiceAPIURL+"notifications/user/"+userid).toPromise();
    }

    saveUserNotification(userfollowing: User, userfollowed: User): Promise<any> {
        const not = <Notification> {
            id: uuid.v4(),
            text: userfollowing.firstname + " " +userfollowing.lastname + " folgt dir jetzt!",
            createdAt: Date.now(),
            read: false
        }

        return this.http.put<Notification>(notificationServiceAPIURL+"notifications/user/"+userfollowed.id, not, httpOptions).toPromise();
    }

    deleteUserNotifications(userid: number): Promise<any> {
        return this.http.delete<any>(notificationServiceAPIURL+"notifications/user/"+userid+"/notifications").toPromise();
    }

    setNotificationToRead(userid: number, nid: string): Promise<any> {
        return this.http.put<Notification>(notificationServiceAPIURL+"notifications/user/"+userid+"/notification/"+nid, null, httpOptions).toPromise();
    }

    //used to cache the users own posting
    public async setNotsObservable(userid: number){
        let nots: Notification[] = await this.getAllUserNotifications(userid);
        if(nots != undefined) {
           this.nots.next(nots);
        }
      }
 



 }