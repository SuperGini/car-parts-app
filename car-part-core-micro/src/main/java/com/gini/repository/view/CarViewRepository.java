package com.gini.repository.view;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.blazebit.persistence.spring.data.repository.EntityViewSpecificationExecutor;
import com.gini.model.Car;
import com.gini.model.views.CarView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CarViewRepository extends EntityViewRepository<CarView, String>, EntityViewSpecificationExecutor<CarView, Car> {

    List<CarView> findAll(Specification<Car> spec);

    Page<CarView> findAll(Specification<Car> spec, Pageable pageable);


}
