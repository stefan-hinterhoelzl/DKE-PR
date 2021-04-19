import { Component, OnInit } from '@angular/core';
import { User } from './model/User';
import { AuthService } from './services/AuthService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Frontend';


  user: User = null;

  constructor(private auth: AuthService) {}

  ngOnInit(): void {
    this.auth.user.subscribe((data:User) => {
      this.user = data;
    });
  }

  logout() {
    this.auth.logout();
  }


}

  
