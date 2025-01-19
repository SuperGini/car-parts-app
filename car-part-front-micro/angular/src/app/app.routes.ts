import { Routes } from '@angular/router';
import {LoginComponent} from './component/login/login.component';
import {SearchPartsComponent} from './component/searchparts/search-parts.component';

export const routes: Routes = [


  {
    path: '',
    redirectTo: '/search/parts',
    pathMatch: 'full',
  },

  { path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
  },

  {
    path: 'search/parts',
    component: SearchPartsComponent,
    pathMatch: 'full'
  }


];
