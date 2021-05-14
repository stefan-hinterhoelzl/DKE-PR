import { CDK_CONNECTED_OVERLAY_SCROLL_STRATEGY_FACTORY } from '@angular/cdk/overlay/overlay-directives';
import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { take } from 'rxjs/operators';
import { DeletePostDialogComponent } from '../delete-post-dialog/delete-post-dialog.component';
import { EditPostDialogComponent } from '../edit-post-dialog/edit-post-dialog.component';
import { Posting } from '../model/Posting';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { PostService } from '../services/postservice';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  posts: Posting[]
  user: User;

  constructor(private ps: PostService, private auth: AuthService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.getPosts();

    this.user = this.auth.currentUserValue;
  }

  getPosts() {
    this.ps.feed.asObservable().subscribe((data) => {
      this.posts = data;
      this.posts.sort((a,b)=> {
        return b.createdAt-a.createdAt;
      });
      console.log(data);
    });
  }

  deletePosting(post: Posting) {
    this.ps.deletePost(post).subscribe((data: any) => {
      console.log(data);
      this.ps.feed.next(this.posts.filter(currpost => currpost.id !== post.id));
    });
  }

  editPosting(post: Posting) {
    this.ps.savePost(post).subscribe((data: any) => {
      console.log(data);
      this.posts = this.posts.filter(currpost => currpost.id !== post.id);
      this.posts.push(post);
      this.ps.feed.next(this.posts)
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

  refresh() {
    this.ps.setFeedObservable(this.user.id.toString());
  }
}
