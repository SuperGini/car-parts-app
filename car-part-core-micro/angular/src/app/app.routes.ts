import { Routes } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {Main} from './components/main/main';

export const routes: Routes = [


  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
  },

  {
    path: 'main',
    component: Main,
    pathMatch: 'full',
  }



];
