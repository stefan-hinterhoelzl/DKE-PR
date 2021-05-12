import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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

  content = new FormControl('', [Validators.required]);
  mood = new FormControl('', [Validators.required]);
  postform: FormGroup;
  user: User;

  constructor(private fb: FormBuilder, private auth: AuthService, private postservice: PostService) { }

  ngOnInit(): void {

    this.postform = this.fb.group({
      content: this.content,
      mood: this.mood
    });
    
    this.user = this.auth.currentUserValue;

  }

  savePost() {
    const payload = <Posting> {
      content: this.content.value,
      mood: this.mood.value,
      createdAt: Date.now(),
      author: this.user.id.toString(),
    }
    
    this.postservice.savePost(payload).subscribe((data: Posting) => {
      console.log(data);

      this.postform.reset()
      Object.keys(this.postform.controls).forEach(key => {
        this.postform.get(key).setErrors(null);
      });
    });
    
  }

  getErrorMessage() {
    if (this.content.hasError('required') || this.mood.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt!"
    }
  }

}
