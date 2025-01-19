import {Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {Main} from './components/main/main';
import {Cars} from './components/main/right/cars/cars';
import {Parts} from './components/main/right/parts/parts';
import {Notfound} from './components/pagenotfound/notfound';
import {SearchPartsComponent} from './components/main/right/searchparts/search-parts.component';

export const routes: Routes = [


  {
    path: 'loginx',
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
      },
      {
        path: 'search-parts',
        title: 'Search Parts',
        component: SearchPartsComponent,
        pathMatch: 'full'
      }
    ]
  },

  {
    path: '**',
    component: Notfound
  }

];
