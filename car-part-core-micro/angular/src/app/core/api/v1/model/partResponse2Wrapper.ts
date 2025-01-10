/**
 * car-part-core openapi spec
 *
 * Contact: blabla@gmail.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
import { PartResponse2 } from './partResponse2';


export interface PartResponse2Wrapper { 
    /**
     * total number of parts from the database. The number is necessary so Angular knows what page number to request
     */
    totalNrOfElements?: number;
    partsResponse?: Array<PartResponse2>;
}

