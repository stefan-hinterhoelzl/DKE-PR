import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { timeStamp } from 'node:console';
import { take } from 'rxjs/operators';
import { DeletePostDialogComponent } from '../delete-post-dialog/delete-post-dialog.component';
import { EditPostDialogComponent } from '../edit-post-dialog/edit-post-dialog.component';
import { Posting } from '../model/Posting';
import { User } from '../model/User';
import { AlertService } from '../services/alertService';
import { AuthService } from '../services/AuthService';
import { PostService } from '../services/postservice';
import { SearchService } from '../services/searchService';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  currentuser: User;
  key: string;
  users: User[] = [];
  posts: Posting[] = [];
  rawdata: any[] = [];

  constructor(private route: ActivatedRoute, private searchService: SearchService, private ps: PostService, private auth: AuthService, private router: Router, private dialog: MatDialog, private alert: AlertService) { }


  ngOnInit(): void {
   this.auth.user.subscribe(data => {
     this.currentuser = data;
   })

    this.route.queryParams.subscribe((params) => {
      this.key = params['key'];
      this.search()
    });
  }


  async search() {
    this.users = await this.searchService.getUsers(this.key).toPromise();
    this.posts = await this.searchService.getPosts(this.key).toPromise();

    if(this.users.length == 0 && this.posts.length == 0) {
      this.alert.error("Keine Ergebnisse");
    }

  }

 
  navigateToUser(user: User) {
    this.router.navigateByUrl[("user/"+user.id)];
  }

  navigateToAuthor(post: Posting) {
    this.router.navigate(['user/'+post.authorid]);
  }



}
