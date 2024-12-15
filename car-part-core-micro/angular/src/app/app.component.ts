import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {FaIconLibrary} from '@fortawesome/angular-fontawesome';
import {faCarSide, faCoffee, faGears} from '@fortawesome/free-solid-svg-icons';
import {faRightFromBracket} from '@fortawesome/free-solid-svg-icons/faRightFromBracket';
import {faRadio} from '@fortawesome/free-solid-svg-icons/faRadio';
import {faMagnifyingGlass} from '@fortawesome/free-solid-svg-icons/faMagnifyingGlass';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

//https://github.com/FortAwesome/angular-fontawesome/blob/main/docs/usage/icon-library.md#using-the-icon-library
export class AppComponent {
  title = 'angular';

  constructor(library: FaIconLibrary) {
    // Add an icon to the library for convenient access in other components
    library.addIcons(
      faRightFromBracket,
      faCarSide,
      faGears,
      faMagnifyingGlass,

    );
  }
}
