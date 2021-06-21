import { Component, Inject, Input, OnDestroy, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { take } from 'rxjs/operators';
import { DeletePostDialogComponent } from '../delete-post-dialog/delete-post-dialog.component';
import { EditPostDialogComponent } from '../edit-post-dialog/edit-post-dialog.component';
import { Posting } from '../model/Posting';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { PostService } from '../services/postservice';

@Component({
  selector: 'app-user-page-postings',
  templateUrl: './user-page-postings.component.html',
  styleUrls: ['./user-page-postings.component.css']
})
export class UserPagePostingsComponent implements OnInit, OnDestroy {

  constructor(private postservice: PostService, private authservice: AuthService, private route: ActivatedRoute, private ps: PostService, public dialog: MatDialog) { }
  posts: Posting[];
  subscription: any;
  postsSubscription: any;
  user: User;
  selfprofile: boolean;


  ngOnInit(): void {
    this.user = this.authservice.currentUserValue;

    this.subscription = this.route.parent.params.subscribe(params => {
      let id: string = params['id'];
      let id2: number = parseInt(id);
      this.initialize(id2);
    });
  }

  ngOnDestroy(): void {
    this.postsSubscription.unsubscribe();
    this.subscription.unsubscribe();
  }

  initialize(id: number) {
    if (this.user.id === id) {
      this.postsSubscription = this.ps.posts.subscribe((data: Posting[]) => {
        this.posts = data;
        this.posts.sort((a,b)=> {
          return b.createdAt-a.createdAt;
        });
        this.selfprofile = true;
      });
    }
    else {
      this.postservice.getAllUserPosts(id).then((data: Posting[]) => {
        this.posts = data;
        this.posts.sort((a,b)=> {
          return b.createdAt-a.createdAt;
        });
        this.selfprofile = false;
      });
    }
  }

  deletePosting(post: Posting) {
    console.log(post);
    this.ps.deletePost(post.id).then((data: any) => {
      this.ps.posts.next(this.posts.filter(currpost => currpost.id !== post.id));
    });
  }

  async editPosting(post: Posting) {
    await this.ps.savePost(post).then((data: any) => {
      this.posts = this.posts.filter(currpost => currpost.id !== post.id);
      this.posts.push(post);
      this.ps.posts.next(this.posts)
    });
  }

  openDeleteDialog(post: Posting) {
    const dialogConfigDelete = new MatDialogConfig();

    dialogConfigDelete.disableClose = true;
    dialogConfigDelete.autoFocus = true;

    dialogConfigDelete.data= {
      description: "Posting Löschen",
      content: "Der Post kann nicht wieder hergestellt werden. Sind Sie sicher?"
    }
    
    const dialogRef = this.dialog.open(DeletePostDialogComponent, dialogConfigDelete);

    dialogRef.afterClosed().pipe(take(1)).subscribe((data) => {
      if (data == true) {
        this.deletePosting(post);
      }
    });
  } 
  openEditDialog(post: Posting) {
    const dialogConfigEdit = new MatDialogConfig();

    dialogConfigEdit.disableClose = true;
    dialogConfigEdit.autoFocus = true;
    dialogConfigEdit.width = "30%"

    dialogConfigEdit.data= {
      content: post.content,
      mood: post.mood,
    }
    
    const dialogRef = this.dialog.open(EditPostDialogComponent, dialogConfigEdit);

    dialogRef.afterClosed().pipe(take(1)).subscribe((data) => {
      if (data != undefined) {
        post.content = data.content;
        post.mood = data.mood;
        this.editPosting(post);
      }
    });
    
  }
    

  

}