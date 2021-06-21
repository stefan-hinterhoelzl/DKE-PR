import { Component, OnInit } from '@angular/core';
import { async } from '@angular/core/testing';
import { Notification } from '../model/Notification';
import { User } from '../model/User';
import { AlertService } from '../services/alertService';
import { AuthService } from '../services/AuthService';
import { NotificationService } from '../services/notificationService';

@Component({
  selector: 'app-user-page-notifications',
  templateUrl: './user-page-notifications.component.html',
  styleUrls: ['./user-page-notifications.component.css']
})
export class UserPageNotificationsComponent implements OnInit {

  notsSubscription: any;
  notifications: Notification[];
  user: User;


  constructor(private ns: NotificationService, private auth: AuthService, private alert: AlertService) { }

  ngOnInit(): void {

    this.notsSubscription = this.ns.nots.subscribe((data) => {
      this.notifications = data;
      this.notifications.sort((a,b)=> {
        return b.createdAt-a.createdAt;
      });
    });

    this.user = this.auth.currentUserValue;
  }

  async deleteAllNotifications() {
    await this.ns.deleteUserNotifications(this.user.id).then(() => {
      this.alert.success("Benachrichtigungen wurden gelöscht.")
      this.ns.nots.next([]);
    },
    (error) =>{
      this.alert.error("Fehler beim Löschen der Benachrichtigungen. "+error)
    });
  }

  async setNotificationToRead(not: Notification) {
    if(not.read == false) {
      await this.ns.setNotificationToRead(this.user.id, not.id).then(() => {
        this.notifications = this.notifications.filter(currnot => currnot.id !== not.id);
        not.read = true;
        this.notifications.push(not);
        this.ns.nots.next(this.notifications);
      }, 
      (error) => {
        this.alert.error("Fehler beim Bearbeiten der Benachrichtigung. "+error);
      });
    }
  }

}
