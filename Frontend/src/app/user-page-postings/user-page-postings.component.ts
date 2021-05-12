import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Posting } from '../model/Posting';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { PostService } from '../services/postservice';

@Component({
  selector: 'app-user-page-postings',
  templateUrl: './user-page-postings.component.html',
  styleUrls: ['./user-page-postings.component.css']
})
export class UserPagePostingsComponent implements OnInit {

  constructor(private postservice: PostService, private authservice: AuthService, private route: ActivatedRoute) { }
  posts: Posting[];
  subscription;


  ngOnInit(): void {
    this.subscription = this.route.parent.params.subscribe(params => {
      let id: string = params['id'];
      this.initialize(id);
    });

  }

  initialize(id: String) {
    this.postservice.getAllUserPosts(id).subscribe((data: Posting[]) => {
      this.posts = data;
      console.log(data);
    });
  }

}
