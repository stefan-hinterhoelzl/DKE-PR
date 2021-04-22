import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FollowingPageComponent } from './following-page/following-page.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { MainComponent } from './main/main.component';
import { ProfileEditComponent } from './profile-edit/profile-edit.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './services/authguard';
import { UserPageComponent } from './user-page/user-page.component';

const routes: Routes = [

{ path: '', pathMatch: 'full', redirectTo: 'app' },

{path: 'login',
component: LoginPageComponent
},
{path: 'register',
component: RegisterComponent
},
{path: 'app',
component: MainComponent,
canActivate: [AuthGuard]
},
{path: 'user/:id',
component: UserPageComponent,
canActivate: [AuthGuard]
},
{path: 'edituser',
component: ProfileEditComponent,
canActivate: [AuthGuard]
},
{path: 'followinglist',
component: FollowingPageComponent,
canActivate: [AuthGuard]
},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
