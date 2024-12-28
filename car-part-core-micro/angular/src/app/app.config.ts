import {ApplicationConfig, ErrorHandler, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideHttpClient, withXsrfConfiguration} from '@angular/common/http';
import {CustomErrorHandler} from './services/error/hadler/custom.error.handler';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes), provideAnimationsAsync(),
    provideHttpClient(withXsrfConfiguration({cookieName: 'XSRF-TOKEN', headerName: 'X-XSRF-TOKEN'})),
    //error handler: -> https://angular.dev/api/core/ErrorHandler?tab=usage-notes
    {provide: ErrorHandler, useClass: CustomErrorHandler},

  ]
};
