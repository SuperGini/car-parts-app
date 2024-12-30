import {inject, Injectable} from '@angular/core';
import {Gateway} from './gateway/gateway';
import {PartManufacturerRequest} from '../core/api/v1';

@Injectable({providedIn: 'root'})
export class PartService {

  gateway = inject(Gateway);

  createPartManufacturer(partManufacturerRequest: PartManufacturerRequest) {
    return this.gateway.createPartManufacturer(partManufacturerRequest);
  }

  getAllPartManufacturers () {
    return this.gateway.getAllPartManufacturers();
  }


}
