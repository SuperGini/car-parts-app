package com.gini.model.views;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.gini.model.CarManufacturer;

@EntityView(CarManufacturer.class)
public interface CarManufacturerView {

    @IdMapping
    String getId();

    String getName();


}
