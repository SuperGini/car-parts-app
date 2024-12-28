import {signal} from '@angular/core';

export const successResponse = signal<string>('');
export const failureResponse = signal<string>('');
