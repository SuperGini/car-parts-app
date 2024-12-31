package com.gini.model.views;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.gini.model.Part;

@EntityView(Part.class)
public interface PartView2 {

    @IdMapping
    String getId();

    String getName();

    String getPartNumber();

    PriceView getPrice();

    CarView2 getCar();

    PartManufacturerView getPartManufacturer();

}
