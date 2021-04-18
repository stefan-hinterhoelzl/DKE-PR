import { Component, OnInit } from '@angular/core';
import { User } from '../model/User';
import { APIService } from '../services/apiservice';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {


  user: User;

  constructor(private apiservice: APIService) { }

  ngOnInit(): void {
  }
  	

  showUser() {
    this.apiservice.getUser().subscribe((data: User) =>  {
      this.user = {...data}
      console.log(this.user)
    });
    
  }
  

}
