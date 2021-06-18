import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { MainComponent } from './main/main.component';
import { RegisterComponent } from './register/register.component';
import { SearchComponent } from './search/search.component';
import { AuthGuard } from './services/authguard';
import { UserPageEditComponent } from './user-page-edit/user-page-edit.component';
import { UserPageFollowingListComponent } from './user-page-following-list/user-page-following-list.component';
import { UserPageNotificationsComponent } from './user-page-notifications/user-page-notifications.component';
import { UserPagePostingsComponent } from './user-page-postings/user-page-postings.component';
import { UserPageComponent } from './user-page/user-page.component';
import { UserPagerFollowersListComponent } from './user-pager-followers-list/user-pager-followers-list.component';

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
{path: 'search',
component: SearchComponent,
canActivate: [AuthGuard]
},
{path: 'user/:id',
component: UserPageComponent,
canActivate: [AuthGuard],
children: [
    {path: '', redirectTo: 'posts', pathMatch: "full"},
    {path: 'posts', component: UserPagePostingsComponent},
    {path: 'edit', component: UserPageEditComponent},
    {path: 'following', component: UserPageFollowingListComponent},
    {path: 'followers', component: UserPagerFollowersListComponent},
    {path: 'notifications', component: UserPageNotificationsComponent},
  ]
},

{path: '**', 
component: MainComponent,
canActivate: [AuthGuard]
}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
