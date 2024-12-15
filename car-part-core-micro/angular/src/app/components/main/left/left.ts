import {Component, ViewEncapsulation} from '@angular/core';
import {faCoffee} from '@fortawesome/free-solid-svg-icons';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {faRightFromBracket} from '@fortawesome/free-solid-svg-icons/faRightFromBracket';
import {MatTooltip, MatTooltipModule} from '@angular/material/tooltip';

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

}
