package com.gini.repository.view;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.gini.model.views.PartView2;

import java.util.Optional;

public interface PartViewRepository extends EntityViewRepository<PartView2, String> {

    Optional<PartView2> findByPartNumber(String partNumber);


}
