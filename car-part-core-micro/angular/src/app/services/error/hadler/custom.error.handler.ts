import {ErrorHandler, Injectable} from '@angular/core';
import * as StackTrace from 'stacktrace-js';

@Injectable({providedIn: 'root'})
export class CustomErrorHandler implements ErrorHandler {

  handleError(error: any): void {

    //todo handle error : -> https://www.telerik.com/blogs/implementing-global-error-handler-angular-step-guide
    // StackTrace.fromError(error).then(stackframes => {
    //   const stringifiedStack = stackframes
    //     .map(sf => sf.toString())
    //     .join('\n');
    //   console.error(stringifiedStack);
    //
    // })

    console.log(error);


  }
}
