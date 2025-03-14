import {inject, Injectable} from '@angular/core';
import {Gateway} from './gateway/gateway';
import {AfPartRequest, PartFilterRequest, PartManufacturerRequest, PartRequest} from '../core/api/v1';

@Injectable({providedIn: 'root'})
export class PartService {

  private gateway = inject(Gateway);

  createPartManufacturer(partManufacturerRequest: PartManufacturerRequest) {
    return this.gateway.createPartManufacturer(partManufacturerRequest);
  }

  getAllPartManufacturers () {
    return this.gateway.getAllPartManufacturers();
  }

  createPart(partRequest:PartRequest) {
    return this.gateway.createPart(partRequest);
  }


  findPartByPartNumber(partNumber: string) {
    return this.gateway.findPartByPartNumber(partNumber)
  }

  createAfPart(afPartRequest: AfPartRequest) {
    return this.gateway.createAfPart(afPartRequest);
  }

  getPartsPaginatedWithFilter(partFilterRequest: PartFilterRequest) {
    return this.gateway.getPartsPaginatedWithFilter(partFilterRequest);
  }
}
