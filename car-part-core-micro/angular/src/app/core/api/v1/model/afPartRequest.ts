/**
 * car-part-core openapi spec
 *
 * Contact: blabla@gmail.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { PriceRequest } from './priceRequest';


export interface AfPartRequest { 
    /**
     * The part id that this af-part coresponds
     */
    partId: string;
    /**
     * part number of af-part
     */
    afPartNumber: string;
    price: PriceRequest;
}

