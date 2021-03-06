import { Component, OnDestroy, OnInit } from '@angular/core';
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
import { FollowerService } from '../services/followerservice';
import { PostService } from '../services/postservice';
import { SearchService } from '../services/searchService';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit, OnDestroy {
  currentuser: User;
  key: string;
  users: any[] = [];
  usersU: User[] = [];
  posts: Posting[] = [];
  rawdata: any[] = [];
  following: number[] = [];
  userSubscription: any;
  followingSubscription: any;
  routeSubscription: any;

  constructor(private route: ActivatedRoute, private searchService: SearchService, private ps: PostService, private auth: AuthService, private router: Router, private dialog: MatDialog, private alert: AlertService, private fs: FollowerService) { }


  ngOnInit(): void {
   this.userSubscription = this.auth.user.subscribe(data => {
     this.currentuser = data;
   })

   this.followingSubscription = this.fs.following.subscribe((data) => {
    this.following = data;
   });

    this.routeSubscription =this.route.queryParams.subscribe((params) => {
      this.key = params['key'];
      this.search()
    });
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
    this.followingSubscription.unsubscribe();
    this.userSubscription.unsubscribe();
  }


  async search() {
    this.usersU = [];
    this.users = await this.searchService.getUsers(this.key);

    if(this.users != undefined) {

      this.users.forEach((element) => {
        const User = <User> {
          id: parseInt(element.id),
          email: element.email,
          firstname: element.firstname,
          lastname: element.lastname,
          phonenumber: element.phonenumber,
          pokemonid: element.pokemonid
        }
        this.usersU.push(User);
      });
    }

    this.posts = await this.searchService.getPosts(this.key);

    if (this.usersU != undefined && this.posts != undefined) {
      if(this.usersU.length == 0 && this.posts.length == 0) {
        this.alert.error("Keine Ergebnisse");
      }
    }
  }

  navigateToUser(user: User) {
    this.router.navigateByUrl[("user/"+user.id)];
  }

  navigateToAuthor(post: Posting) {
    this.router.navigate(['user/'+post.authorid]);
  }


  async followUser(user: User) {
    await this.fs.followUser(this.currentuser, user).then(() =>{
      this.following.push(user.id);
      this.fs.following.next(this.following);
      this.alert.success(user.firstname +" abonniert!");
    });
  }

  async unfollowUser(user: User){
    await this.fs.UnfollowUser(this.auth.currentUserValue.id, user.id).then(() =>{
      let i = this.following.indexOf(user.id);
      this.following.splice(i, 1);
      this.fs.following.next(this.following);
    });

  }


}
