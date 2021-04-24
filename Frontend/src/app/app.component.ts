import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
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
  searchterm = new FormControl();
  options: string[] = ['One', 'Two', 'Three'];
  filteredOptions: Observable<string[]>;

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

  
