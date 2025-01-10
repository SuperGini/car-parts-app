package com.gini.repository.filters;

import com.gini.dto.PartFilterRequest;
import com.gini.model.Car;
import com.gini.model.Car_;
import com.gini.model.Part;
import com.gini.model.PartManufacturer;
import com.gini.model.Part_;
import com.gini.model.Price;
import com.gini.model.Price_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * https://stackoverflow.com/questions/77464478/creating-dynamic-entity-views-with-blazebit-persistence
 * https://persistence.blazebit.com/documentation/1.6/entity-view/manual/en_US/index.html#fetching-a-data-subset
 */

@RequiredArgsConstructor
public class PartFilterSpec implements Specification<Part> {

    private final PartFilterRequest partFilter;

    @Override
    public Predicate toPredicate(Root<Part> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {


        List<Predicate> predicates = new ArrayList<>();

        Join<Part, PartManufacturer> partManufacturerJoin = root.join(Part_.PART_MANUFACTURER, JoinType.INNER);
        Join<Part, Car> partCarJoin = root.join(Part_.CAR, JoinType.INNER);
        Join<Part, Price> partPriceJoin = root.join(Part_.PRICE, JoinType.INNER);

        if (!StringUtils.isEmpty(partFilter.getCarModel())) {
            var condition = criteriaBuilder.equal(partCarJoin.get(Car_.MODEL), partFilter.getCarModel());
//            predicates.add(criteriaBuilder.and(condition));
            predicates.add(condition);
        }


        if (partFilter.getStartPrice() != null && partFilter.getEndPrice() != null) {
            var condition = criteriaBuilder.between(partPriceJoin.get(Price_.PART_PRICE), partFilter.getStartPrice(), partFilter.getEndPrice());
            predicates.add(condition);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
