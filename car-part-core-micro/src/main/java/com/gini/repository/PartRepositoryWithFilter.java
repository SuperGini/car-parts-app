package com.gini.repository;

import com.gini.model.*;
import com.gini.repository.filters.PartFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class PartRepositoryWithFilter {

    @PersistenceContext
    private EntityManager em;

    public List<Part> findPartWithFilter(PartFilter filter) {


        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Part> partRoot = cq.from(Part.class);


        Join<Part, PartManufacturer> manufacturerJoin = partRoot.join("partManufacturer", JoinType.INNER);
        Join<Part, Price> priceJoin = partRoot.join("price", JoinType.INNER);
        Join<Part, Car> carJoin = partRoot.join("car", JoinType.INNER);
        Join<Car, CarManufacturer> carManufacturerJoin= carJoin.join("carManufacturer", JoinType.INNER);



        if(Objects.nonNull(filter.getPartName())) {
//            cq.where(cb.equal(partRoot.get("name"), filter.getPartName()));
            predicates.add(cb.equal(partRoot.get("name"), filter.getPartName()));
        }

        if(Objects.nonNull(filter.getCarModel())) {
//            carJoin.on(cb.equal(carJoin.get("model"), filter.getCarModel()));
            predicates.add(cb.equal(carJoin.get("model"), filter.getCarModel()));
        }

        if(Objects.nonNull(filter.getStartDate())) {
//            carJoin.on(cb.greaterThanOrEqualTo(carJoin.get("productionStartYear"), filter.getStartDate()));
            predicates.add(cb.greaterThanOrEqualTo(carJoin.get("productionStartYear"), filter.getStartDate()));
        }

        if(Objects.nonNull(filter.getEndDate())) {
//            carJoin.on(cb.lessThanOrEqualTo(carJoin.get("productionEndYear"), filter.getEndDate()));
            predicates.add(cb.lessThanOrEqualTo(carJoin.get("productionEndYear"), filter.getEndDate()));
        }

        if(Objects.nonNull(filter.getStartPrice()) && Objects.nonNull(filter.getEndPrice())) {
//            priceJoin.on(cb.between(priceJoin.get("partPrice"), filter.getStartPrice(), filter.getEndPrice()));
            predicates.add(cb.between(priceJoin.get("partPrice"), filter.getStartPrice(), filter.getEndPrice()));
        }

        cq.multiselect(partRoot, manufacturerJoin, priceJoin, carJoin, carManufacturerJoin).where(predicates.toArray(new Predicate[0]));

        em.createQuery(cq).getResultList()
                .stream()
                .peek(x -> {
                    System.out.println(x.get(0));
                    System.out.println(x.get(1));
                    System.out.println(x.get(2));
                    System.out.println(x.get(3));
                    System.out.println(x.get(4));
                })
                .forEach(x -> {

                });






        return null;

    }

}
