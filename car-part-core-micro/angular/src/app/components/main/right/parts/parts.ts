import {Component, inject, OnInit} from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatOption} from '@angular/material/core';
import {MatSelect} from '@angular/material/select';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {CarResponse2, Currency, PartManufacturerRequest, PartManufacturerResponse} from '../../../../core/api/v1';
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


  carModels: CarResponse2[];
  partManufacturers: PartManufacturerResponse[];

  currencies = Object.values(Currency);

  protected readonly successResponse = successResponse;
  partManufacturerName = new FormControl('', Validators.required);

  partService = inject(PartService);
  carManufacturerService = inject(CarManufacturerService);


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
}
