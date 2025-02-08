import {inject, Injectable} from '@angular/core';
import {Gateway} from './gateway';
import {PartFilterRequest} from '../core/api/v1';

@Injectable({providedIn: 'root'})
export class PartService {

  protected gateway = inject(Gateway);


  postForPartsSearch (partFilterRequest: PartFilterRequest) {
    return this.gateway.postForPartsSearch(partFilterRequest);
  }

}
