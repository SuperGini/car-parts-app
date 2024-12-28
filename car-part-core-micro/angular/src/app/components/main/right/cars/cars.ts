import {Component, inject} from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatSelect} from '@angular/material/select';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {CarManufacturerRequest, CarService} from '../../../../core/api/v1';
import {successResponse} from '../../../../services/cache/cache';
import {CarManufacturerService} from '../../../../services/car.manufacturer.service';
import {MatOption} from '@angular/material/core';

@Component({
  selector: 'cars-component',
  templateUrl: 'cars.html',
  imports: [
    FormsModule,
    MatFormField,
    MatSelect,
    MatOption,
    MatLabel,
    MatInput,
    MatButton,
    ReactiveFormsModule

  ],
  styleUrl: 'cars.scss'
})
export class Cars {

  carManufacturer = new FormControl('', [Validators.required]);
  protected readonly successResponse = successResponse;
  timeout = setTimeout(() => {
    console.log("++");
    successResponse.set(``)

  }, 10000);

  carService = inject(CarService);
  carManufacturerService = inject(CarManufacturerService);

  createCarManufacturer() {
    const carManufacturerRequest: CarManufacturerRequest = {
      name: this.carManufacturer.value
    }

    this.carManufacturerService.createCarManufacturer(carManufacturerRequest)
      .subscribe(response => {
        successResponse.set(`car manufacturer ${response.name} was created successfully`);
        setTimeout(() => {
          successResponse.set(``)
        }, 5000);
      });
  }

}


