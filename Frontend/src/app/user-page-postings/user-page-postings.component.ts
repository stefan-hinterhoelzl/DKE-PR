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

  constructor(private postservice: PostService, private authservice: AuthService, private route: ActivatedRoute, private ps: PostService) { }
  posts: Posting[];
  subscription;
  user: User;
  selfprofile: boolean;


  ngOnInit(): void {
    this.user = this.authservice.currentUserValue;

    this.subscription = this.route.parent.params.subscribe(params => {
      let id: string = params['id'];
      this.initialize(id);
    });

  }

  initialize(id: String) {
    if (this.user.id.toString() === id) {
      this.ps.posts.asObservable().subscribe((data: Posting[]) => {
        this.posts = data;
        this.posts.sort((a,b)=> {
          return b.createdAt-a.createdAt;
        });
        console.log(data);
        this.selfprofile = true;
      });
    }
    else {
      this.postservice.getAllUserPosts(id).subscribe((data: Posting[]) => {
        this.posts = data;
        this.posts.sort((a,b)=> {
          return b.createdAt-a.createdAt;
        });
        console.log(data);
        this.selfprofile = false;
      });
    }
  }

  deletePosting(post: Posting) {
    this.ps.deletePost(post).subscribe((data: any) => {
      console.log(data);
      this.ps.posts.next(this.posts.filter(currpost => currpost.id !== post.id));
    });
  }

}
