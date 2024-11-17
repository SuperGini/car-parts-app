package com.gini.model.views;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.gini.model.Car;

import java.util.List;

@EntityView(Car.class)
public interface CarView {

    @IdMapping
    String getId();

    String getModel();

//    @Mapping(fetch = FetchStrategy.MULTISET)
//    CarManufacturerView getManufacturer();

    @Mapping(fetch = FetchStrategy.MULTISET)
    List<PartView> getParts();



}
