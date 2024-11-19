package com.gini.mapper;

import com.gini.dto.AfPartRequest;
import com.gini.dto.AfPartResponse;
import com.gini.dto.PriceResponse;
import com.gini.model.AftermarketPart;
import com.gini.model.Currency;
import com.gini.model.Price;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AfPartMapper {


    public static AftermarketPart mapFrom(AfPartRequest afPartRequest){

        var price = new Price();
        price.setPartPrice(afPartRequest.getPrice().getPrice());
        price.setCurrency(Currency.valueOf(afPartRequest.getPrice().getCurrency().toString()));

        AftermarketPart ap = new AftermarketPart();
        ap.setAfPartNumber(afPartRequest.getAfPartNumber());
        ap.setPrice(price);

        return ap;
    }

    public static AfPartResponse mapFrom(AftermarketPart ap){

        var afPartResponse = new AfPartResponse();
        var priceResponse = new PriceResponse();

        priceResponse.setPrice(ap.getPrice().getPartPrice());
        priceResponse.setCurrency(com.gini.dto.Currency.valueOf(ap.getPrice().getCurrency().toString()));

        afPartResponse.setId(ap.getAfPartNumber());
        afPartResponse.setAfPartNumber(ap.getId());
        afPartResponse.setPrice(priceResponse);

        return afPartResponse;
    }



}
