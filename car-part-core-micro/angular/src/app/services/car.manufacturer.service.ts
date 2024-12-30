import {inject, Injectable} from '@angular/core';
import {Gateway} from './gateway/gateway';
import {CarManufacturerRequest, CarRequest} from '../core/api/v1';

@Injectable({providedIn: 'root'})
export class CarManufacturerService {

  gateway = inject(Gateway);

  createCarManufacturer (carManufacturer: CarManufacturerRequest) {
    return this.gateway.createManufacturer(carManufacturer);
  }

  getAllCarrManufacturers () {
    return this.gateway.getAllCarrManufacturers();
  }

  createCare(careRequest: CarRequest) {
    return this.gateway.createCar(careRequest)
  }

  getAllCars() {
    return this.gateway.getAllCars();
  }



}
