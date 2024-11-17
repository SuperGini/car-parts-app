package com.gini.repository;

import com.gini.dto.AfDto;
import com.gini.dto.CarDto;
import com.gini.dto.PartDto;
import com.gini.mapper.DtoMapper;
import com.gini.model.AftermarketPart;
import com.gini.model.Car;
import com.gini.model.Car_;
import com.gini.model.Part;
import com.gini.model.Part_;
import com.gini.repository.filters.CarFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class CustomAfPartRepository {

    @PersistenceContext
    private EntityManager em;


    public void getAllPartsForCar(CarFilter carFilter) {


        var entityGraph = em.createEntityGraph(Car.class);


        List<Predicate> predicates = new ArrayList<>();


        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Tuple> query = builder.createTupleQuery();

        Root<Car> root = query.from(Car.class);

        Join<Car, Part> joinPart = root.join(Car_.parts);
        Join<Part, AftermarketPart> afJoin = joinPart.join(Part_.aftermarketPart, JoinType.LEFT);

        var modelPredicate = builder.equal(root.get(Car_.MODEL), carFilter.carModel());
        predicates.add(modelPredicate);


//        query.multiselect(root, joinPart, afJoin).where(builder.and(predicates.toArray(new Predicate[0])));
        query.multiselect(root, joinPart, afJoin);


//        Map<String, Car> cars = new HashMap<>(100);
        Map<String, CarDto> dtos = new HashMap<>(100);
        Map<String, PartDto> partsDtos = new HashMap<>(100);
        Map<String, AfDto> afDtos = new HashMap<>(100);


        var q = em.createQuery(query);


        q.getResultList()
                .stream()
                .forEach(tuple -> {

                    String carId = ((Car) tuple.get(0)).getId();
                    var car = (Car) tuple.get(0);
                    var part = (Part) tuple.get(1);
                    var afPart = (AftermarketPart) tuple.get(2);

                    var carDto = DtoMapper.toCarDto(car);
                    var partDto = DtoMapper.toPartDto(part);
                    var afDto = DtoMapper.afDto(afPart);

                    dtos.computeIfAbsent(carId, x -> carDto);

                    var y = dtos.get(carId);

                    if (!y.getParts().contains(partDto)) {
                        y.getParts().add(partDto);
                    }

                    if (y.getParts().contains(partDto)) {
                        partDto.getAfDtos().add(afDto);

                    }

                    var indx = y.getParts().indexOf(afDto);

                    y.getParts().get(indx).getAfDtos().add(afDto);


//                    partDto.getAfDtos().add(afDto);
//                    carDto.getParts().add(partDto);

                    System.out.println(tuple.get(0) + ", " + tuple.get(1) + ", " + tuple.get(2));

                });


        List<CarDto> carsX = new ArrayList<>(dtos.values());

        carsX.forEach(x -> {
            System.out.println(x);
            System.out.println(x.getParts());
            x.getParts().forEach(y -> System.out.println(y.getAfDtos()));
            System.out.println("---------------------------------------");
        });


    }

    public void getAllPartsForCarWithEntityGRaph(CarFilter carFilter) {

        //todo: it doesn't work it throws ----> MultipleBagFetchException  because i fetch 2 collections at the same time
        List<Predicate> predicates = new ArrayList<>();

        var carGraph = em.createEntityGraph(Car.class);
//        carGraph.addAttributeNodes(Car_.PARTS);

        var subgraphAfPart = carGraph.addSubgraph(Car_.parts);
        subgraphAfPart.addAttributeNodes(Part_.AFTERMARKET_PART);


        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);

        Join<Car, Part> joinPart = root.join(Car_.parts);
//        Join<Part, AftermarketPartNumber> afJoin = joinPart.join(Part_.aftermarketPartNumbers, JoinType.LEFT);

        var modelPredicate = builder.equal(root.get(Car_.MODEL), carFilter.carModel());
        predicates.add(modelPredicate);

        query.where(builder.and(predicates.toArray(new Predicate[0])));


//        query.multiselect(root, joinPart, afJoin).where(builder.and(predicates.toArray(new Predicate[0])));

        var q = em.createQuery(query)
                .setHint("jakarta.persistence.loadgraph", carGraph);


        q.getResultList()
                .forEach(car -> {
                    System.out.println(car.toString());
                    System.out.println(car.getParts());


                });


    }


    /**
     *
     * https://stackoverflow.com/questions/30459904/criteria-api-and-entity-graph
     * Map<UUID, Car> carMap = new HashMap<>();
     *
     * q.getResultList()
     *     .stream()
     *     .forEach(tuple -> {
     *         UUID carId = (UUID) tuple.get(0).getId();
     *         Car car = carMap.computeIfAbsent(carId, id -> (Car) tuple.get(0));
     *
     *         Part part = (Part) tuple.get(1);
     *         car.getParts().add(part);
     *
     *         AftermarketPartNumber aftermarketPartNumber = (AftermarketPartNumber) tuple.get(2);
     *         if (aftermarketPartNumber != null) {
     *             part.getAftermarketPartNumbers().add(aftermarketPartNumber);
     *         }
     *     });
     *
     * List<Car> cars = new ArrayList<>(carMap.values());
     *
     *
     *
     *
     * */


}
