import {Component} from '@angular/core';
import {Left} from './left/left';
import {Right} from './right/right';


@Component({
  selector: "main-component",
  templateUrl: "main.html",
  imports: [
    Left,
    Right
  ],
  styleUrl: "main.scss"
})
export class Main {

}
