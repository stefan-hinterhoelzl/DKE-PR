import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { take } from 'rxjs/operators';
import { DeletePostDialogComponent } from '../delete-post-dialog/delete-post-dialog.component';
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

  constructor(private postservice: PostService, private authservice: AuthService, private route: ActivatedRoute, private ps: PostService, public dialog: MatDialog) { }
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

  openDeleteDialog(post: Posting) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data= {
      description: "Posting Löschen",
      content: "Der Post kann nicht wieder hergestellt werden. Sind Sie sicher?"
    }
    
    const dialogRef = this.dialog.open(DeletePostDialogComponent, dialogConfig);

    dialogRef.afterClosed().pipe(take(1)).subscribe((data) => {
      if (data == true) {
        this.deletePosting(post);
      }
    });
  }  
    

  

}