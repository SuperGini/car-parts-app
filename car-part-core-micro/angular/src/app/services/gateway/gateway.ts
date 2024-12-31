import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {environment} from '../../../environments/environment';
import {
  AfPartRequest, AfPartResponse,
  CarManufacturerRequest,
  CarManufacturerResponse,
  CarRequest,
  CarResponse, CarResponse2,
  PartManufacturerRequest, PartManufacturerResponse, PartRequest, PartResponse, PartResponse2
} from '../../core/api/v1';

@Injectable({providedIn: 'root'})
export class Gateway {

  protected httpClient = inject(HttpClient);

  //car--------------------------------------------

  createManufacturer (carManufacturer: CarManufacturerRequest) {
    return this.httpClient.post<CarManufacturerResponse>(`${environment.apiUrl}/car/car-manufacturer`, carManufacturer);
  }

  getAllCarrManufacturers () {
    return this.httpClient.get<CarManufacturerResponse[]>(`${environment.apiUrl}/car/manufacturers`);
  }

  createCar(carRequest: CarRequest) {
    return this.httpClient.post<CarResponse>(`${environment.apiUrl}/car`, carRequest);
  }

  getAllCars() {
    return this.httpClient.get<CarResponse2[]>(`${environment.apiUrl}/car/cars`);
  }

  //part----------------------------------------------

  createPartManufacturer(partManufacturerRequest: PartManufacturerRequest) {
    return this.httpClient.post<PartManufacturerResponse>(`${environment.apiUrl}/part/part-manufacturer`, partManufacturerRequest);
  }

  getAllPartManufacturers() {
    return this.httpClient.get<PartManufacturerResponse[]>(`${environment.apiUrl}/part/manufacturers`);
  }

  createPart(partRequest: PartRequest){
    return this.httpClient.post<PartResponse>(`${environment.apiUrl}/part`, partRequest);
  }

  findPartByPartNumber(partNumber: string) {
    return this.httpClient.get<PartResponse2>(`${environment.apiUrl}/part/find/${partNumber}`);
  }

  createAfPart(afPartRequest: AfPartRequest) {
    return this.httpClient.post<AfPartResponse>(`${environment.apiUrl}/af-part`, afPartRequest);
  }

}
