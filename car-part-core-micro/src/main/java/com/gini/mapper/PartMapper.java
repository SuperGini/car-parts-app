package com.gini.mapper;

import com.gini.dto.*;
import com.gini.model.Currency;
import com.gini.model.Part;
import com.gini.model.PartManufacturer;
import com.gini.model.Price;
import com.gini.model.views.PartView2;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PartMapper {

    public PartManufacturer mapPartManufacturer(PartManufacturerRequest partManufacturer) {
        var manufacturer = new PartManufacturer();
        manufacturer.setName(partManufacturer.getName());
        return manufacturer;
    }

    public PartManufacturerResponse mapPartManufacturerResponse(PartManufacturer partManufacturer) {
        return new PartManufacturerResponse().id(UUID.fromString(partManufacturer.getId()))
                .name(partManufacturer.getName());
    }


    public Part mapPart(PartRequest request) {
        var part = new Part();
        var price = new Price();

        part.setPartNumber(request.getPartNumber());
        part.setName(request.getName());

        price.setPartPrice(request.getPrice().getPrice());
        price.setCurrency(Currency.valueOf(request.getPrice().getCurrency().toString()));

        //set price on part and part on price
        part.setPrice(price);
        price.setPart(part);


        return part;
    }

    public PartResponse mapPartResponse(Part part) {

        var price = new PriceResponse()
                .price(part.getPrice().getPartPrice())
                .currency(com.gini.dto.Currency.valueOf(part.getPrice().getCurrency().toString()));

        return new PartResponse()
                .id(UUID.fromString(part.getId()))
                .name(part.getName())
                .partNumber(part.getPartNumber())
                .price(price);
    }

    public PartResponse2 mapPartResponse2(PartView2 part) {
        var price = new PriceResponse()
                .price(part.getPrice().getPartPrice())
                .currency(com.gini.dto.Currency.valueOf(part.getPrice().getCurrency().toString()));

        return new PartResponse2()
                .id(UUID.fromString(part.getId()))
                .name(part.getName())
                .partNumber(part.getPartNumber())
                .model(part.getCar().getModel())
                .manufacturer(part.getPartManufacturer().getName())
                .price(price);
    }

}
