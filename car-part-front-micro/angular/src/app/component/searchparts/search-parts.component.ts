import {Component, inject} from '@angular/core';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatSlider, MatSliderRangeThumb} from '@angular/material/slider';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef,
  MatRow, MatRowDef,
  MatTable
} from '@angular/material/table';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {CdkDragDrop} from '@angular/cdk/drag-drop';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {PartService} from '../../services/partService';
import {PartFilterRequest, PartResponse2} from '../../core/api/v1';


@Component({
  selector: 'search-parts-component',
  templateUrl: 'search-parts.html',
  imports: [
    MatFormField,
    MatInput,
    MatSlider,
    MatSliderRangeThumb,
    MatLabel,
    FaIconComponent,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow,
    MatPaginator,
    ReactiveFormsModule,
    FormsModule,
    MatHeaderCellDef,
    MatCellDef,
    MatRowDef,
    MatHeaderRowDef
  ],
  styleUrl: 'search-parts.scss'
})
export class SearchPartsComponent {

  displayedColumns: string[] = ['name', 'partNumber', 'price', 'manufacturer', 'model'];
  dataSource: PartResponse2[];
  // dataSource: String[];


  protected partName = new FormControl<string>(null, Validators.required);
  protected carModel = new FormControl<string>(null, Validators.required);

  protected minX = 0;
  protected maxX = 50000;
  protected min = 0;
  protected max = 50000;
  protected step = 10;
  protected length = 0;


  protected partService = inject(PartService);

  searchByParName() {
    const partFilterRequest: PartFilterRequest = {
      page: 0,
      pageElements: 5,
      carModel: this.carModel.value,
      manufacturerName: null,
      partName: this.partName.value,
      partManufacturerName: null,
      startPrice: Number(this.min),
      endPrice: this.max,
    }
    console.log("+++++++++++++++++")

    this.partService.postForPartsSearch(partFilterRequest)
      .subscribe(parts => {
        this.dataSource = parts.partsResponse;
        this.length = parts.totalNrOfElements;
      });
  }

  searchByParName2(e: PageEvent) {
    console.log(`page index: ${e.pageIndex}  pageSize: ${e.pageSize}`);

    const partFilterRequest: PartFilterRequest = {
      page: e.pageIndex,
      pageElements: e.pageSize,
      carModel: this.carModel.value,
      manufacturerName: null,
      partName: this.partName.value,
      partManufacturerName: null,
      startPrice: Number(this.min),
      endPrice: this.max,
    }

     console.log("+++++ elements: " + partFilterRequest.pageElements + "  page: " + partFilterRequest.page);

    this.partService.postForPartsSearch(partFilterRequest)
      .subscribe(parts => {
        this.dataSource = parts.partsResponse;
        this.length = parts.totalNrOfElements;
      });

  }


}




