import {Component, inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {
  CarResponse2,
  Currency,
  PartManufacturerRequest,
  PartManufacturerResponse,
  PartRequest, PriceRequest
} from '../../../../core/api/v1';
import {PartService} from '../../../../services/part.service';
import {successResponse} from '../../../../services/cache/cache';
import {CarManufacturerService} from '../../../../services/car.manufacturer.service';
import {switchMap} from 'rxjs';

@Component({
  selector: 'parts-component',
  templateUrl: 'parts.html',
  imports: [
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    MatOption,
    MatSelect,
    ReactiveFormsModule,
    FaIconComponent
  ],
  styleUrl: 'parts.scss'
})
export class Parts implements OnInit {

  protected carModels: CarResponse2[];
  protected partManufacturers: PartManufacturerResponse[];
  protected currencies = Object.values(Currency);

  protected readonly successResponse = successResponse;
  protected partManufacturerName = new FormControl('', Validators.required);

  protected partForm = new FormGroup({
    partName: new FormControl('', Validators.required),
    partNumber: new FormControl('', Validators.required),
    price: new FormControl('', Validators.required),
    currency: new FormControl('', Validators.required),
    carObj: new FormControl('', Validators.required),
    partManufacturerObj: new FormControl('', Validators.required)
  })

  protected partService = inject(PartService);
  protected carManufacturerService = inject(CarManufacturerService);


  ngOnInit(): void {
    this.partService.getAllPartManufacturers()
      .subscribe(response => this.partManufacturers = response);

    this.carManufacturerService.getAllCars().subscribe(response => this.carModels = response);
  }

  createPartManufacturer() {
    const pmRequest: PartManufacturerRequest = {
      name: this.partManufacturerName.value
    }

    this.partService.createPartManufacturer(pmRequest)
      .pipe(
        switchMap(response => {
          successResponse.set(`part manufacturer ${response.name} was created successfully`);
          this.deleteSuccessResponse();
          return this.partService.getAllPartManufacturers();
        })
      )
      .subscribe(
        response_2 => {
          this.partManufacturers = response_2;
        }
      )
  }


  private deleteSuccessResponse() {
    setTimeout(() => successResponse.set(``), 5000);
  }

  createPart() {

    console.log("currency is:" + <Currency> this.partForm.value.currency)

    const priceRequest: PriceRequest = {
      price: Number(this.partForm.value.price),
      currency: <Currency> this.partForm.value.currency
    }

    const partRequest: PartRequest = {
      name: this.partForm.value.partName,
      partNumber: this.partForm.value.partNumber,
      price: priceRequest,
      carId: (<CarResponse2> <unknown> this.partForm.value.carObj).id,
      manufacturerId: (<PartManufacturerResponse> <unknown> this.partForm.value.partManufacturerObj).id
    }

    this.partService.createPart(partRequest)
      .subscribe(response => {
        console.log(`new part id: ${response.id}`);
      })

  }
}
