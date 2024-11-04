package com.gini.repository;


import com.gini.model.Car;
import com.gini.model.CarManufacturer;
import com.gini.model.Part;
import com.gini.model.Price;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomCarRepository {

    @PersistenceContext
    private EntityManager em;


    public void getCars() {


        CriteriaBuilder builder = em.getCriteriaBuilder(); //create critaria builder

        CriteriaQuery<Car> cq = builder.createQuery(Car.class); //create query


        Root<Car> customerRoot = cq.from(Car.class);

        cq.select(customerRoot); // SELECT c FROM Car c


        TypedQuery<Car> query = em.createQuery(cq);

        query.getResultList().forEach(System.out::println);


    }

    public void getCarName() {


        CriteriaBuilder builder = em.getCriteriaBuilder(); //create critaria builder

        CriteriaQuery<String> cq = builder.createQuery(String.class); //create query


        Root<Car> carRoot = cq.from(Car.class); // rootul este Car

        cq.select(carRoot.get("model")); // SELECT c.name FROM Car c


        TypedQuery<String> query = em.createQuery(cq);

        query.getResultList().forEach(System.out::println);


    }

    public void getCarNameAndId() {


        CriteriaBuilder builder = em.getCriteriaBuilder(); //create critaria builder

        CriteriaQuery<Object[]> cq = builder.createQuery(Object[].class); //create query


        Root<Car> carRoot = cq.from(Car.class); // rootul este Car

//        cq.multiselect(carRoot.get("model"), carRoot.get("id")); //SELECT c.name, c.id FROM Car c

        cq.multiselect(carRoot.get("model"), builder.count(carRoot.get("id")))                          // SELECT c.name, c.id FROM Car c
                .where(builder.notEqual(carRoot.get("id"), "e13776d1-f139-48df-a37f-ef660850fe37")) //WHERE id <> "e13776d1-f139-48df-a37f-ef660850fe37" (not equal)
                .orderBy(builder.desc(carRoot.get("id")))                                               // ORDER BY c.id DESC
                .groupBy(carRoot.get("id"));                                                            // GROUP BY -> ca sa mearga count-ul de mai sus


        //todo: la fel ca mai sus dar adaugam clauze in functie de conditii
//        cq.multiselect(carRoot.get("model"), carRoot.get("id"));
//        if(condition) {
//            cq.orderBy(builder.desc(carRoot.get("id")));
//        }
//        cq.where(builder.notEqual(carRoot.get("id"), "e13776d1-f139-48df-a37f-ef660850fe37"));

//        Create a predicate for testing whether the first argument is greater than or equal to the second.
//        builder.greaterThanOrEqualTo(x, y);


        TypedQuery<Object[]> query = em.createQuery(cq);

        query.getResultList().forEach(x -> System.out.println(x[0] + " " + x[1]));


    }

    //asta bubuie -> ca sa mearga trebuie sa facem cu multiselect, din cauza la leazy initialization exception
    public void getCarWithJoinManufacturer() {

        CriteriaBuilder builder = em.getCriteriaBuilder(); //create critaria builder

        CriteriaQuery<Car> cq = builder.createQuery(Car.class); //create query

        Root<Car> carRoot = cq.from(Car.class); // rootul este Car

        carRoot.join("carManufacturer", JoinType.INNER); //SELECT c FROM Car c JOIN c.manufacturer ON manufacturer.id = car.manufacturer_id;



        cq.select(carRoot).where(builder.equal(carRoot.get("id"), "e13776d1-f139-48df-a37f-ef660850fe37"));



        TypedQuery<Car> query = em.createQuery(cq);

        query.getResultList().forEach(car -> {
            System.out.println(car);
            System.out.println(car.getCarManufacturer()); //va genera lazy initialization exception
        });

    }



    //JOINS-------------------

    public void joinsPart2() {

        var builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = builder.createTupleQuery(); //Tuple e o colectie de genul map care ne ajuta sa incarcam mai multe colectii deodata -> Tuple<Car, Manufacturer, Price>

        Root<Car> carRoot = cq.from(Car.class); //SELECT c FROM Car c
        Join<Car, CarManufacturer> joinCarManufacturer = carRoot.join("carManufacturer", JoinType.LEFT); //default INNER JOIN
        Join<Car, Part> joinParts = carRoot.join("parts", JoinType.LEFT);
        Join<Part, Price> joinPrice = joinParts.join("price", JoinType.LEFT);





//        em.getCriteriaBuilder()
//                .createTupleQuery()
//                .multiselect(carRoot, join);


        cq.multiselect(carRoot, joinCarManufacturer,joinParts, joinPrice); //SELECT c, m FROM Car c INNER JOIN CarManufacturer m.......


        var q = em.createQuery(cq);

        q.getResultList()
                .forEach(x -> {
            System.out.println(x.get(0) + " " + x.get(1)+ " " + x.get(2) + " " + x.get(3));
        });

    }

    public void subquery () {

    var builder  = em.getCriteriaBuilder();
    var mainQuery = builder.createQuery(Car.class);

    Root<Car> carRoot = mainQuery.from(Car.class);


    //SELECT c, (SELECT count(p) FROM Part p JOIN Car c ON p.id IN c.parts) n FROM Car c Where n > 1

    Subquery<Long> subquery = mainQuery.subquery(Long.class);
    Root<Car> subRootCar = subquery.correlate(carRoot);

    Join<Car, Part> carPartJoin = subRootCar.join("parts", JoinType.LEFT);

    subquery.select(builder.count(carPartJoin));

    mainQuery.select(carRoot)
            .where(builder.greaterThan(subquery, 1L));

    var q = em.createQuery(mainQuery);

    q.getResultList().forEach(System.out::println);




    }








}
