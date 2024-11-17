package com.gini.model.views;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

@EntityView(com.gini.model.AftermarketPart.class)
public interface AftermarketPartView {

    @IdMapping
    String getId();

    String getAfPartNumber();


}
