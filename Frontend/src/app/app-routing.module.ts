import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { MainComponent } from './main/main.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './services/authguard';

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
canActivate: [AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
