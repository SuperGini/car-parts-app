import {Injectable, signal} from "@angular/core";

@Injectable({providedIn: 'root'})
export class ErrorStorage {

  errorSignal_1 = signal<string>(null);

}
