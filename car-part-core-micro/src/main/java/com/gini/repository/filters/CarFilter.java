package com.gini.repository.filters;

import java.math.BigDecimal;

public record CarFilter(
        String carModel,
        String manufacturerName,
        String partName,
        String partManufacturerName,
        BigDecimal startPrice,
        String endPrice
) {
}
