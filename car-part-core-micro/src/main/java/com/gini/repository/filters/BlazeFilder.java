package com.gini.repository.filters;

import com.blazebit.persistence.spring.data.repository.BlazeSpecification;
import com.gini.dto.PartFilterRequest;
import com.gini.model.Car;
import com.gini.model.Car_;
import com.gini.model.Part;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BlazeFilder implements Specification<Car> {

    private final PartFilterRequest carFilter;

    @Override
    public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();


        var x = criteriaBuilder.equal(root.get(Car_.MODEL), carFilter.getCarModel());
        predicates.add(x);



        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
