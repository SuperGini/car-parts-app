package com.gini.model.views;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.gini.model.PartManufacturer;

@EntityView(PartManufacturer.class)
public interface PartManufacturerView {

    @IdMapping
    String getId();

    String getName();
}
