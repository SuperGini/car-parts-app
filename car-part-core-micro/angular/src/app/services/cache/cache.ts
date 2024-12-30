import {signal} from '@angular/core';
import {Observable, of, Subject} from 'rxjs';
import {CarManufacturerResponse} from '../../core/api/v1';

export const successResponse = signal<string>('');
export const failureResponse = signal<string>('');

export const cmSubject: Subject<CarManufacturerResponse[]> = new Subject<[]>();
