import { CdkTextareaAutosize } from '@angular/cdk/text-field';
import { Component, NgZone, OnInit, ViewChild } from '@angular/core';
import { validateBasis } from '@angular/flex-layout';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DateRange } from '@angular/material/datepicker';
import { take } from 'rxjs/operators';
import { Posting } from '../model/Posting';
import { User } from '../model/User';
import { AuthService } from '../services/AuthService';
import { PostService } from '../services/postservice';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.css']
})
export class PostCreateComponent implements OnInit {

  constructor(private fb: FormBuilder, private auth: AuthService, private postservice: PostService, private _ngZone: NgZone, private ps: PostService) { 
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

  savePost() {
    const payload = <Posting> {
      id: uuidv4(),
      authorname: this.user.firstname + " " +this.user.lastname,
      authorid: this.user.id.toString(),
      mood: this.emoji.value,
      createdAt: Date.now(),
      content: this.content.value
    }
    
    this.postservice.savePost(payload).subscribe((data: Posting) => {
      this.content.reset();
      this.emoji.reset();
      let currPostings:Posting[] =this.ps.userPosts;
      currPostings.push(payload);
      this.ps.posts.next(currPostings);
    });
  }


  getErrorMessage() {
    if (this.content.hasError('required') || this.emoji.hasError('required')) {
      return "Feld darf nicht leer sein"
    }

  }
}

