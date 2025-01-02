package com.gini.repository.view;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.blazebit.persistence.spring.data.repository.EntityViewSpecificationExecutor;
import com.gini.model.Part;
import com.gini.model.views.PartView2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface PartViewRepository extends EntityViewRepository<PartView2, String>, EntityViewSpecificationExecutor<PartView2, Part> {

    Optional<PartView2> findByPartNumber(String partNumber);

    Page<PartView2> findAll(Specification<Part> spec, Pageable pageable);


}
