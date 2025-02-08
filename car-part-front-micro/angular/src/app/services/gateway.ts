import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PartFilterRequest, PartResponse2Wrapper} from '../core/api/v1';
import {environment} from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class Gateway {

  protected httpClient = inject(HttpClient);


  postForPartsSearch(partFilterRequest: PartFilterRequest) {
    return this.httpClient.post<PartResponse2Wrapper>(`${environment.apiUrl}/part/core-micro/filter2`, partFilterRequest);
  }


}

