import { Component, OnInit } from '@angular/core';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { FollowerService } from '../services/followerservice';

@Component({
  selector: 'app-user-page-following-list',
  templateUrl: './user-page-following-list.component.html',
  styleUrls: ['./user-page-following-list.component.css']
})
export class UserPageFollowingListComponent implements OnInit {

  users: User[] = [];
  following: number[] = [];

  constructor(private auth: AuthService, private fs: FollowerService) { }

  ngOnInit() {

    this.fs.following.subscribe(async (data) => {
      this.following = data;
      this.users = await this.auth.getUsersPerList(data);
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
