import { Component, OnInit } from '@angular/core';
import { Notification } from '../model/Notification';

@Component({
  selector: 'app-user-page-notifications',
  templateUrl: './user-page-notifications.component.html',
  styleUrls: ['./user-page-notifications.component.css']
})
export class UserPageNotificationsComponent implements OnInit {

  notifications: Notification[] = [];


  constructor() { }

  ngOnInit(): void {
    const not1 = <Notification> {
      id: "1",
      text: "Testnotification",
      createdAt: 1624267816,
      read: false,
    }

    const not2 = <Notification> {
      id: "2",
      text: "Testnotification 2",
      createdAt: 1624267816,
      read: true,
    }

    const not3 = <Notification> {
      id: "3",
      text: "Testnotification 3",
      createdAt: 1624267816,
      read: false,
    }

    this.notifications.push(not1);
    this.notifications.push(not2);
    this.notifications.push(not3);
  }

}
