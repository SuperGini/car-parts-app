package com.gini.model.views;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.gini.model.Car;

@EntityView(Car.class)
public interface CarView2 {

    @IdMapping
    String getId();

    String getModel();

}
