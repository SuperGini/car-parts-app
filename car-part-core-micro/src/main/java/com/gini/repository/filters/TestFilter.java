package com.gini.repository.filters;

import com.gini.model.*;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TestFilter implements Specification<Part> {

    private final PartFilter filter;

    @Override
    public Predicate toPredicate(Root<Part> partRoot, CriteriaQuery<?> query, CriteriaBuilder critBuilder) {


        List<Predicate> predicates = new ArrayList<>();

        Join<Part, PartManufacturer> partManufacturerJoin = partRoot.join("partManufacturer", JoinType.INNER);
        Join<Part, Price> priceJoin = partRoot.join("price", JoinType.INNER);
        Join<Part, Car> carJoin = partRoot.join("car", JoinType.INNER);
        Join<Car, CarManufacturer> carManufacturerJoin = carJoin.join("carManufacturer", JoinType.INNER);

        if (filter.getPartName() != null) {
            var condition = critBuilder.equal(partRoot.get("name"), filter.getPartName());

            predicates.add(condition);

        }

        if (filter.getCarModel() != null) {
            var condition = critBuilder.equal(carJoin.get("model"), filter.getCarModel());

            predicates.add(condition);

        }

        if (filter.getStartDate() != null) {
            var condition = critBuilder.greaterThanOrEqualTo(carJoin.get("productionStartYear"), filter.getStartDate());
            predicates.add(condition);
        }

        if (filter.getEndDate() != null) {
            var condittion = critBuilder.lessThanOrEqualTo(carJoin.get("productionEndYear"), filter.getEndDate());
            predicates.add(condittion);
        }

        if (filter.getStartPrice() != null && filter.getEndPrice() != null) {
            var condition = critBuilder.between(priceJoin.get("partPrice"), filter.getStartPrice(), filter.getEndPrice());
            predicates.add(condition);
        }


//            query.multiselect(partRoot, partManufacturerJoin, priceJoin, carJoin, carManufacturerJoin);


        return critBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
