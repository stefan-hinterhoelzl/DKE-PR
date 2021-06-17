import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { take } from 'rxjs/operators';
import { DeletePostDialogComponent } from '../delete-post-dialog/delete-post-dialog.component';
import { MyErrorStateMatcher } from '../helpers/MyErrorStateMatcher';
import { PasswordChangeCredential } from '../model/PasswordChangeCredential';
import { User } from '../model/User';
import { AlertService } from '../services/alertService';
import { AuthService } from '../services/AuthService';
import { PostService } from '../services/postservice';

@Component({
  selector: 'app-user-page-edit',
  templateUrl: './user-page-edit.component.html',
  styleUrls: ['./user-page-edit.component.css']
})
export class UserPageEditComponent implements OnInit {

  routeUserID: string; 
  dataform: FormGroup;
  pwform: FormGroup;
  matcher: MyErrorStateMatcher = new MyErrorStateMatcher();
  user: User;
  
  firstname = new FormControl('', [Validators.required]);
  lastname = new FormControl('', [Validators.required]);
  phonenumber = new FormControl('');

  oldpw = new FormControl('', [Validators.required]);
  newpw = new FormControl('', [Validators.required]);
  newpwconfirm = new FormControl('');

  constructor(private route: ActivatedRoute, private fb: FormBuilder, private router: Router, private auth: AuthService, private alertservice: AlertService, private dialog: MatDialog, private ps: PostService) { }

  ngOnInit(): void {

    this.route.parent.params.subscribe(params => {
      this.routeUserID = params['id'];
      this.user = JSON.parse(localStorage.getItem('user'))
      if (this.user.id.toString() != this.routeUserID) {
        this.router.navigate(['/user/'+this.routeUserID+"/posts"])
      }
      
    });

    this.dataform = this.fb.group({
      firstname: this.firstname,
      lastname: this.lastname,
      phonenumber: this.phonenumber,
    });

    this.pwform = this.fb.group({
      oldpw: this.oldpw,
      newpw: this.newpw,
      newpwconfirm: this.newpwconfirm    
    },
    {
      validator: [this.checkSamePasswords]
      
    }   
    );
  }


  async updateUserData() {
    let changes = <User> {
      firstname:  this.firstname.value,
      lastname:  this.lastname.value,
      phonenumber:  this.phonenumber.value,
      pokemonid: this.user.pokemonid,
    }   

    //save to database
    await this.auth.updateUser(changes, this.user.id).then((data: User) => {
      //on success, cache the data and change the observable
      this.user = data;
      localStorage.setItem('user', JSON.stringify(data));
      this.auth.user.next(data);


      this.dataform.reset();

      Object.keys(this.dataform.controls).forEach(key => {
      this.dataform.get(key).setErrors(null);
      this.alertservice.success("Benutzer "+data.email+ " wurde aktualsiert.");
    });
    },
    (error) => {
      this.alertservice.error(error.error);
      }
    );

    
  }

 async updateUserPassword() {
    let payload: PasswordChangeCredential = {
      oldpassword: this.oldpw.value,
      newpassword: this.newpw.value,
      newpasswordconfirm: this.newpwconfirm.value,
    }
    await this.auth.updateUserPassword(payload, this.user.id).then((data: User) => {
      this.dataform.reset();

      Object.keys(this.dataform.controls).forEach(key => {
      this.dataform.get(key).setErrors(null);

      this.alertservice.success("Passwort wurde aktualisiert. Sie werden abgemeldet");
      setTimeout(() => this.auth.logout(),3000);

    });
    },
    (error) => {
      this.alertservice.error(error.error);
    }
    );
  }

  getErrorMessage() {
    if (this.firstname.hasError('required') || this.lastname.hasError('required')) {
      return "Nicht alle Pflichtfelder wurden ausgefüllt!"
    }
  }

  checkSamePasswords(group: FormGroup) {
    let pass = group.controls.newpw.value;
    let confirmPass = group.controls.newpwconfirm.value;
    return pass === confirmPass ? null : { notSame: true };
  }


  async deleteUser() {
    await this.auth.deleteUser(this.user.id).then(async () => {

      await this.ps.deleteAllByAuthor(this.user.id);
      
      this.alertservice.success("Der User wurde erfolgreich gelöscht. Auf Wiedersehen!");
      setTimeout(() => this.auth.logout(),3000);
      
    },
    (error) => {
      console.log(error)
      this.alertservice.error("Fehler beim Löschen des Users");
    }
    );

  }

  openDeleteDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    dialogConfig.data= {
      description: "Benutzer " + this.user.email + " Löschen?",
      content: "Der Benutzer kann nicht wieder hergestellt werden. Sind Sie sicher?"
    }
    
    const dialogRef = this.dialog.open(DeletePostDialogComponent, dialogConfig);

    dialogRef.afterClosed().pipe(take(1)).subscribe((data) => {
      if (data == true) {
        this.deleteUser();
      }
    });
  }  

}
