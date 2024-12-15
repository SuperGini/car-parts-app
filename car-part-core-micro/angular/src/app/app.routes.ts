import {Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {Main} from './components/main/main';
import {Cars} from './components/main/right/cars/cars';
import {Parts} from './components/main/right/parts/parts';
import {Notfound} from './components/pagenotfound/notfound';

export const routes: Routes = [


  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
  },

  {
    path: '',
    redirectTo: '/main/cars',
    pathMatch: 'full',
  },

  {
    path: 'main',
    component: Main,
    children: [
      {
        path: 'cars',
        title: 'Cars',
        component: Cars,
        pathMatch: 'full',

      },
      {
        path: 'parts',
        title: 'Parts',
        component: Parts,
        pathMatch: 'full',
      }
    ]
  },

  {
    path: '**',
    component: Notfound
  }

];
