import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { SearchService } from '../services/searchService';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  currentuser: User;
  key: string;
  users: User[] = []
  rawdata: any[] = [];

  constructor(private route: ActivatedRoute, private searchService: SearchService, private auth: AuthService, private router: Router) { }


  ngOnInit(): void {
   this.auth.user.subscribe(data => {
     this.currentuser = data;
   })

    this.route.queryParams.subscribe((params) => {
      this.key = params['key'];
      this.search()
    });
  }


  search() {
    this.searchService.getUsers(this.key).subscribe((data: User[]) => {
      this.users = data;
      console.log(this.users);
    });
  }

  navigateToUser(user: User) {
    this.router.navigateByUrl[("user/"+user.id)];
  }



}
