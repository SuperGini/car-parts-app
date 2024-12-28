import {inject, Injectable} from '@angular/core';
import {Gateway} from './gateway/gateway';
import {CarManufacturerRequest} from '../core/api/v1';

@Injectable({providedIn: 'root'})
export class CarManufacturerService {

  gateway = inject(Gateway);

  createCarManufacturer (carManufacturer: CarManufacturerRequest) {
    return this.gateway.createManufacturer(carManufacturer);
  }



}
