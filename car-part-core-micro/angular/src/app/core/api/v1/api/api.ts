export * from './afPart.service';
import { AfPartService } from './afPart.service';
export * from './car.service';
import { CarService } from './car.service';
export * from './part.service';
import { PartService } from './part.service';
export const APIS = [AfPartService, CarService, PartService];
