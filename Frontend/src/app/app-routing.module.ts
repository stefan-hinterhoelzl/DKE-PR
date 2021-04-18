import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { MainComponent } from './main/main.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [

{ path: '', pathMatch: 'full', redirectTo: 'signin' },

{path: 'login',
component: LoginPageComponent
},
{path: 'register',
component: RegisterComponent
},
{path: 'app',
component: MainComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
