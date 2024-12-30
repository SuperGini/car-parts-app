import {Component, inject, OnInit, signal} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatSelect} from '@angular/material/select';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {CarManufacturerRequest, CarManufacturerResponse, CarRequest, CarService} from '../../../../core/api/v1';
import {successResponse} from '../../../../services/cache/cache';
import {CarManufacturerService} from '../../../../services/car.manufacturer.service';
import {MatOption} from '@angular/material/core';
import {switchMap} from 'rxjs';

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
export class Cars implements OnInit {

  carManufacturer = new FormControl('', [Validators.required]);
  protected readonly successResponse = successResponse;
  carManufacturers = signal<CarManufacturerResponse[]>(null); //merge direct cu variabila nu trebuie neparat signal
  carFormGroup = new FormGroup({
    model: new FormControl('', [Validators.required]),
    productionStartYear: new FormControl('', [Validators.required]),
    productionEndYear: new FormControl('', [Validators.required]),
    carManufacturerObj: new FormControl('', [Validators.required]),
  });


  carService = inject(CarService);
  carManufacturerService = inject(CarManufacturerService);


  ngOnInit(): void {
    this.carManufacturerService.getAllCarrManufacturers()
      .subscribe(response => this.carManufacturers.set(response));
  }

  createCarManufacturer() {
    const carManufacturerRequest: CarManufacturerRequest = {
      name: this.carManufacturer.value
    }

    this.carManufacturerService.createCarManufacturer(carManufacturerRequest)
      .pipe(
        // I do the first call and then switchMap waits for the response to come, and I can use this response in the second
        //call. I need to return an observable from switchMap.
        switchMap(carManufactureResponse => {

          successResponse.set(`car manufacturer ${carManufactureResponse.name} was created successfully`);
          this.deleteSuccessResponse();
          return this.carManufacturerService.getAllCarrManufacturers()
        })
      )
      .subscribe(response => this.carManufacturers.set(response));
  }

  addCar() {

    const startYear = new Date();
    startYear.setFullYear(2010 - Math.random() * 100, 12, 20); //because I was to lazy to send it from UI

    const endYear = new Date();
    endYear.setFullYear(2010 + Math.random() * 100, 12, 20); //because I was to lazy to send it from UI


    const carRequest : CarRequest = {
      model: this.carFormGroup.value.model,
      productionStartYear: startYear.toISOString(),
      productionEndYear: endYear.toISOString(),
      carManufacturerId: (<CarManufacturerResponse><unknown>this.carFormGroup.value.carManufacturerObj).id
    }

    this.carManufacturerService.createCare(carRequest)
      .subscribe(carResponse => {
        console.log(JSON.stringify(carResponse));
      })
  }

  private deleteSuccessResponse() {
    setTimeout(() => successResponse.set(``), 5000);
  }


}


