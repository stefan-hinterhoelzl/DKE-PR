import { CdkTextareaAutosize } from '@angular/cdk/text-field';
import { Component, Inject, NgZone, OnInit, ViewChild } from '@angular/core';
import { validateBasis } from '@angular/flex-layout';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DateRange } from '@angular/material/datepicker';
import { take } from 'rxjs/operators';
import { Posting } from '../model/Posting';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { PostService } from '../services/postservice';
import { v4 as uuidv4 } from 'uuid';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AlertService } from '../services/alertService';

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.css']
})
export class PostCreateComponent implements OnInit {

  constructor(private fb: FormBuilder, private auth: AuthService, private postservice: PostService, private _ngZone: NgZone, private ps: PostService, public dialog: MatDialog, public alert: AlertService) { 
    this.content = new FormControl('', Validators.required)
    this.emoji = new FormControl('', Validators.required)
  }

  @ViewChild('autosize') autosize: CdkTextareaAutosize;
  user: User;
  content: FormControl
  emoji: FormControl
  toggled: boolean = false;
  emojies: String[] = ["\uD83D\uDE00","\ud83d\ude02","\ud83d\ude05","\ud83d\ude07","\ud83d\ude08","\ud83d\ude09","\ud83d\ude0a","\ud83d\ude0d","\ud83d\ude0e","\ud83d\ude14","\ud83d\ude20"];


  ngOnInit(): void {
  this.user = this.auth.currentUserValue;
  }

  
  triggerResize() {
    this._ngZone.onStable.pipe(take(1))
        .subscribe(() => this.autosize.resizeToFitContent(true));
  }

  async savePost() {
    const payload = <Posting> {
      id: uuidv4(),
      authorname: this.user.firstname + " " +this.user.lastname,
      authorid: this.user.id,
      mood: this.emoji.value,
      createdAt: Date.now(),
      content: this.content.value
    }
  
    await this.postservice.savePost(payload).then(() => {
      this.content.reset();
      this.emoji.reset();
      let currPostings:Posting[] =this.ps.userPosts;
      let currFeed: Posting[] = this.ps.feedPosts;
      currPostings.push(payload);
      currFeed.push(payload);
      this.ps.posts.next(currPostings);
      this.ps.feed.next(currFeed);
      this.alert.success("Posting wurde abgegeben.")
    });
  }


  getErrorMessage() {
    if (this.content.hasError('required') || this.emoji.hasError('required')) {
      return "Feld darf nicht leer sein"
    }

  }
}




