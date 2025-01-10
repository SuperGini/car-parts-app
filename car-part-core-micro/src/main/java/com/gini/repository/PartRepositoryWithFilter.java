package com.gini.repository;

import com.gini.model.*;
import com.gini.repository.filters.PartFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
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


    public Specification<Part> findPartWithFilter2(PartFilter filter) {

        Specification<Part> partSpec = (partRoot, query, critBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<Part, PartManufacturer> partManufacturerJoin = partRoot.join("partManufacturer", JoinType.INNER);
            Join<Part, Price> priceJoin = partRoot.join("price", JoinType.INNER);
            Join<Part, Car> carJoin = partRoot.join("car", JoinType.INNER);
            Join<Car, CarManufacturer> carManufacturerJoin= carJoin.join("carManufacturer", JoinType.INNER);

            if(filter.getPartName() != null) {
               var condition =  critBuilder.equal(partRoot.get("name"), filter.getPartName());
               predicates.add(condition);

            }

            if(filter.getCarModel() != null) {
                var condition = critBuilder.equal(carJoin.get("model"), filter.getCarModel());
                predicates.add(condition);
            }

            if(filter.getStartDate() != null) {
                var condition = critBuilder.greaterThanOrEqualTo(carJoin.get("productionStartYear"), filter.getStartDate());
                predicates.add(condition);
            }

            if(filter.getEndDate() != null) {
                var condittion = critBuilder.lessThanOrEqualTo(carJoin.get("productionEndYear"), filter.getEndDate());
                predicates.add(condittion);
            }

            if(filter.getStartPrice() != null && filter.getEndPrice() != null) {
                var condition = critBuilder.between(priceJoin.get("partPrice"), filter.getStartPrice(), filter.getEndPrice());
                predicates.add(condition);
            }


            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        };




        return partSpec;

    }


    public void withCriteriaBuilder(PartFilter filter) {

        CriteriaBuilder critBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = critBuilder.createTupleQuery();
        Root<Part> partRoot = cq.from(Part.class);

        List<Predicate> predicates = new ArrayList<>();
        List<Selection<?>> selections = new ArrayList<>();

        Join<Part, PartManufacturer> partManufacturerJoin = partRoot.join(Part_.partManufacturer, JoinType.INNER);
        Join<Part, Price> priceJoin = partRoot.join(Part_.price, JoinType.INNER);
        Join<Part, Car> carJoin = partRoot.join(Part_.car, JoinType.INNER);
        Join<Car, CarManufacturer> carManufacturerJoin= carJoin.join(Car_.carManufacturer, JoinType.INNER);

        selections.add(partRoot);
        selections.add(partManufacturerJoin);
        selections.add(priceJoin);
        selections.add(carJoin);
        selections.add(carManufacturerJoin);

        if(filter.getPartName() != null) {
            var condition =  critBuilder.equal(partRoot.get(Part_.NAME), filter.getPartName());
            predicates.add(condition);
        }

        if(filter.getCarModel() != null) {
            var condition = critBuilder.equal(carJoin.get(Car_.MODEL), filter.getCarModel());
            predicates.add(condition);
        }

        if(filter.getStartDate() != null) {
            var condition = critBuilder.greaterThanOrEqualTo(carJoin.get(Car_.PRODUCTION_START_YEAR), filter.getStartDate());
            predicates.add(condition);
        }

        if(filter.getEndDate() != null) {
            var condition = critBuilder.lessThanOrEqualTo(carJoin.get(Car_.productionEndYear), filter.getEndDate());
            predicates.add(condition);
        }

        if(filter.getStartPrice() != null && filter.getEndPrice() != null) {
            var condition = critBuilder.between(priceJoin.get(Price_.PART_PRICE), filter.getStartPrice(), filter.getEndPrice());
            predicates.add(condition);
        }

        cq.multiselect(selections).where(predicates.toArray(new Predicate[0]));

       var x =  em.createQuery(cq)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();
           x.forEach(y -> {

               System.out.println(y.get(0) + ", " + y.get(1) + ", " + y.get(2) + ", " + y.get(3) + " " + y.get(4));
           });
    }



    public void criteria2WithSpecification(Specification<Part> spec) {

        CriteriaBuilder critBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = critBuilder.createTupleQuery();
        Root<Part> partRoot = cq.from(Part.class);

        List<Selection<?>> selections = new ArrayList<>();

        Join<Part, PartManufacturer> partManufacturerJoin = partRoot.join(Part_.partManufacturer, JoinType.INNER);
        Join<Part, Price> priceJoin = partRoot.join(Part_.price, JoinType.INNER);
        Join<Part, Car> carJoin = partRoot.join(Part_.car, JoinType.INNER);
        Join<Car, CarManufacturer> carManufacturerJoin= carJoin.join(Car_.carManufacturer, JoinType.INNER);

        selections.add(partRoot);
        selections.add(partManufacturerJoin);
        selections.add(priceJoin);
        selections.add(carJoin);
        selections.add(carManufacturerJoin);


        cq.multiselect(selections).where(spec.toPredicate(partRoot,cq, critBuilder));

        var x =  em.createQuery(cq)
                .setFirstResult(0)
                .setMaxResults(1)
                .getResultList();
        x.forEach(y -> {

            System.out.println(y.get(0) + ", " + y.get(1) + ", " + y.get(2) + ", " + y.get(3) + " " + y.get(4));
        });





    }






}
