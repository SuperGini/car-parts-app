import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatOption, MatSelect} from '@angular/material/select';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {HttpClient} from '@angular/common/http';

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
    MatButton

  ],
  styleUrl: 'cars.scss'
})
export class Cars {

  carManufacturer: string;
  httpClient = inject(HttpClient);

  createCarManufacturer() {

    console.log(this.carManufacturer + " +++++++");
    const x: CarManufacturer = {
      name: this.carManufacturer
    }

      this.httpClient.post('//localhost:9090/car/car-manufacturer', x, { withCredentials: true })
        .subscribe();


  }
}

interface CarManufacturer {
  name: string;
}
