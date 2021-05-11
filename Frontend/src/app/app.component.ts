import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
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
  searchbar = new FormControl();
  

  constructor(private auth: AuthService, public router: Router) {}

  ngOnInit(): void {
    this.auth.user.subscribe((data:User) => {
      this.user = data;
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
  


}

  
