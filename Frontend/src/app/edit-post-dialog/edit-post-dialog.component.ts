import { CdkTextareaAutosize } from '@angular/cdk/text-field';
import { Component, Inject, NgZone, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-edit-post-dialog',
  templateUrl: './edit-post-dialog.component.html',
  styleUrls: ['./edit-post-dialog.component.css']
})
export class EditPostDialogComponent implements OnInit {
  
  currcontent;
  currmood;
  content: FormControl
  emoji: FormControl
  toggled: boolean = false;
  emojies: String[] = ["\uD83D\uDE00","\ud83d\ude02","\ud83d\ude05","\ud83d\ude07","\ud83d\ude08","\ud83d\ude09","\ud83d\ude0a","\ud83d\ude0d","\ud83d\ude0e","\ud83d\ude14","\ud83d\ude20"];


  constructor(private dialogRef: MatDialogRef<EditPostDialogComponent>, @Inject(MAT_DIALOG_DATA) data, private _ngZone: NgZone) {
    this.currcontent = data.content;
    this.currmood = data.mood;
    console.log(this.currmood)
    this.content = new FormControl(this.currcontent, Validators.required)
    this.emoji = new FormControl(this.currmood, Validators.required)
   }
   
   @ViewChild('autosize') autosize: CdkTextareaAutosize;

  ngOnInit(): void {
  }

  triggerResize() {
    this._ngZone.onStable.pipe(take(1))
        .subscribe(() => this.autosize.resizeToFitContent(true));
  }

  close() {
    this.dialogRef.close();
  }

  save() {
    const data = {
      content: this.content.value,
      mood: this.emoji.value,
    }
    this.dialogRef.close(data);
  }



  getErrorMessage() {
    if (this.content.hasError('required') || this.emoji.hasError('required')) {
      return "Feld darf nicht leer sein"
    }

  }

}
