import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterComponent } from './register/register.component';
import { AngularMaterialModule } from './angular-material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MainComponent } from './main/main.component';
import {AuthService } from './services/AuthService';
import { AuthGuard } from './services/authguard';
import { BootstrapModule } from './bootstrap.module';
import { UserPageComponent } from './user-page/user-page.component';
import { UserPageFollowingListComponent } from './user-page-following-list/user-page-following-list.component';
import { UserPageEditComponent } from './user-page-edit/user-page-edit.component';
import { UserPagePostingsComponent } from './user-page-postings/user-page-postings.component';
import { ErrorInterceptor } from './helpers/ErrorInterceptor';
import { JwtInterceptor } from './helpers/JwtInterceptor';
import { AlertComponent } from './alert/alert.component';
import { SearchbarComponent } from './searchbar/searchbar.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    RegisterComponent,
    MainComponent,
    UserPageComponent,
    UserPageFollowingListComponent,
    UserPageEditComponent,
    UserPagePostingsComponent,
    AlertComponent,
    SearchbarComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    AngularMaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BootstrapModule
  ],
  providers: [AuthService, AuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}

  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
