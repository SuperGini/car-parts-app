import {Component, inject, ViewEncapsulation} from '@angular/core';
import {faCoffee} from '@fortawesome/free-solid-svg-icons';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {faRightFromBracket} from '@fortawesome/free-solid-svg-icons/faRightFromBracket';
import {MatTooltip, MatTooltipModule} from '@angular/material/tooltip';
import {Router} from '@angular/router';

@Component({
  selector: 'left-component',
  templateUrl: 'left.html',
  imports: [
    FaIconComponent,
    MatTooltip
  ],
  encapsulation: ViewEncapsulation.None, // this is for the angular tooltip to work
  styleUrl: 'left.scss'
})
export class Left {

  private router: Router = inject(Router);


  routeToCarsPage(){
    this.router.navigate(['main/cars']);
  }

  routeToPartsPage(){
    this.router.navigate(['main/parts']);
  }

  routeToSearchPartsPage(){

  }

  routeToSearchCarsPage(){

  }


}
