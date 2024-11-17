package com.gini.model.views;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.gini.model.Part;

import java.util.List;

@EntityView(Part.class)
public interface PartView {

    @IdMapping
    String getId();

//    String getPartNumber();

    String getName();

    PriceView getPrice();

    @Mapping(fetch = FetchStrategy.MULTISET)
    List<AftermarketPartView> getAftermarketPart();



}
