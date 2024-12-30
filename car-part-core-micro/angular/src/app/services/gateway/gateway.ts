import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {environment} from '../../../environments/environment';
import {CarManufacturerRequest, CarManufacturerResponse, CarRequest, CarResponse} from '../../core/api/v1';

@Injectable({providedIn: 'root'})
export class Gateway {

  httpClient = inject(HttpClient);

  createManufacturer (carManufacturer: CarManufacturerRequest) {
    return this.httpClient.post<CarManufacturerResponse>(`${environment.apiUrl}/car/car-manufacturer`, carManufacturer);
  }

  getAllCarrManufacturers () {
    return this.httpClient.get<CarManufacturerResponse[]>(`${environment.apiUrl}/car/manufacturers`);
  }

  createCar(carRequest: CarRequest) {
    return this.httpClient.post<CarResponse>(`${environment.apiUrl}/car`, carRequest);
  }


}
