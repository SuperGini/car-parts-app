package com.gini.repository.filters;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PartFilter {

    private String partName;
    private String carManufacturer;
    private String carModel;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal startPrice;
    private BigDecimal endPrice;


}
