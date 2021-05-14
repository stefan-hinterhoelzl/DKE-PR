import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-post-dialog',
  templateUrl: './delete-post-dialog.component.html',
  styleUrls: ['./delete-post-dialog.component.css']
})
export class DeletePostDialogComponent implements OnInit {

  description: string;
  content: string;


  constructor(private dialogRef: MatDialogRef<DeletePostDialogComponent>, @Inject(MAT_DIALOG_DATA) data) {
    this.description = data.description;
    this.content = data.content;
   }

  ngOnInit(): void {
  }

  close() {
    this.dialogRef.close();
  }

  delete() {
    this.dialogRef.close(true);
  }

}
