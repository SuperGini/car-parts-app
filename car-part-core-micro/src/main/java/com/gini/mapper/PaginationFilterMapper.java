package com.gini.mapper;

import com.gini.dto.AfPartFilterResponse;
import com.gini.dto.CarFilterResponse;
import com.gini.dto.Currency;
import com.gini.dto.PartFilterResponse;
import com.gini.dto.PriceResponse;
import com.gini.model.views.CarView;
import com.gini.model.views.PartView;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationFilterMapper {

    public static CarFilterResponse toCarFilterResponse(CarView carView) {
        var partsResponse = new ArrayList<PartFilterResponse>();

        carView.getParts().forEach(part -> {

            var afParts = new ArrayList<AfPartFilterResponse>();
            mapToAfPartResponse(part, afParts);
            var partResponse = createPartResponse(part, afParts);
            partsResponse.add(partResponse);

        });

        return new CarFilterResponse()
                .id(carView.getId())
                .model(carView.getModel())
                .parts(partsResponse);
    }

    private static PartFilterResponse createPartResponse(PartView part, ArrayList<AfPartFilterResponse> afParts) {
        return new PartFilterResponse()
                .id(part.getId())
                .name(part.getName())
                .afParts(afParts)
                .price(new PriceResponse()
                        .price(part.getPrice().getPartPrice())
                        .currency(Currency.fromValue(part.getPrice().getCurrency().toString())));
    }

    private static void mapToAfPartResponse(PartView part, ArrayList<AfPartFilterResponse> afParts) {
        part.getAftermarketPart().forEach(afterMarketPart -> {
            var af = new AfPartFilterResponse()
                    .id(afterMarketPart.getId())
                    .afPartNumber(afterMarketPart.getAfPartNumber());
            afParts.add(af);

        });
    }


}
