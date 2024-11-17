package com.gini.model.views;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.gini.model.Currency;
import com.gini.model.Price;

import java.math.BigDecimal;

@EntityView(Price.class)
public interface PriceView {

    @IdMapping
    String getId();

    BigDecimal getPartPrice();

    Currency getCurrency();





}
