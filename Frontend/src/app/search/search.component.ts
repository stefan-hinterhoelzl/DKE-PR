import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from '../model/User';
import { SearchService } from '../services/searchService';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  key: string;
  Users: User[]

  constructor(private route: ActivatedRoute, private searchService: SearchService) { }


  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.key = params['key'];
      this.search()
    });
  }


  search() {
    this.searchService.getUsers(this.key).subscribe((data: User[]) => {
      this.Users = {... data};
      console.log(this.Users);
    })
  }



}
