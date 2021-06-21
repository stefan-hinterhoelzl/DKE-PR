import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from './model/User';
import { AuthService } from './services/AuthService';
import { NotificationService } from './services/notificationService';
import { Notification} from '../app/model/Notification';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Frontend';


  user: User = null;
  searchbar = new FormControl();
  userSubscription: any;
  notsSubscription: any;
  notification: Notification[];
  unread: Notification[];
  badge: string;
  

  constructor(private auth: AuthService, public router: Router, private ns: NotificationService) {}

  ngOnInit(): void {
    this.userSubscription = this.auth.user.subscribe((data: User) => {
      this.user = data;
    });

    this.notsSubscription = this.ns.nots.subscribe((data: Notification[]) => {
      this.notification = data;
      this.unread = data.filter((curr) => curr.read == false);
    });
  }

  logout() {
    this.auth.logout();
  }


  search() {
    console.log("searching");
    let searchterm: string = this.searchbar.value;
    if (searchterm == undefined) searchterm = " ";
    this.router.navigate(['/search'], {queryParams:{'key': searchterm}});
  }

  navigateToNots() {
    this.router.navigate(['user/'+this.user.id+'/notifications']);
  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
    this.notsSubscription.unsubscribe();
  }
  


}

  
