package com.gini.repository;


import com.gini.model.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

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

    public void getCarWithJoinManufacturer() {


        CriteriaBuilder builder = em.getCriteriaBuilder(); //create critaria builder

        CriteriaQuery<Car> cq = builder.createQuery(Car.class); //create query

        Root<Car> carRoot = cq.from(Car.class); // rootul este Car

        carRoot.join("carManufacturer", JoinType.INNER); //SELECT c FROM Car c JOIN c.manufacturer ON manufacturer.id = car.manufacturer_id;


        cq.select(carRoot);



        TypedQuery<Car> query = em.createQuery(cq);

        query.getResultList().forEach(System.out::println);


    }


}
