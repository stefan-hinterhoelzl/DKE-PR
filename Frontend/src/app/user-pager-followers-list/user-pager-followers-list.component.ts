import { Component, OnInit } from '@angular/core';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { FollowerService } from '../services/followerservice';

@Component({
  selector: 'app-user-pager-followers-list',
  templateUrl: './user-pager-followers-list.component.html',
  styleUrls: ['./user-pager-followers-list.component.css']
})
export class UserPagerFollowersListComponent implements OnInit {

  users: User[] = [];
  followers: number[] = [];

  constructor(private auth: AuthService, private fs: FollowerService) { }

  ngOnInit() {

    this.fs.followers.subscribe(async (data) => {
      this.followers = data;
      console.log(data);
      this.users = await this.auth.getUsersPerList(data);
      console.log(this.users);
    });
  }

  async unfollowUser(user: User){
    await this.fs.UnfollowUser(this.auth.currentUserValue.id, user.id).then(() =>{
      let i = this.followers.indexOf(user.id);
      this.followers.splice(i, 1);
      this.fs.following.next(this.followers);
    });

  }

}
