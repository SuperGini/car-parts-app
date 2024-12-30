package com.gini.repository;

import com.gini.model.Car;
import com.gini.repository.projection.CarProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, String> {

    //https://vladmihalcea.com/spring-jpa-dto-projection/
    @Query("""
                    SELECT new com.gini.repository.projection.CarProjection(c.id, c.model)
                     FROM Car c
            """)
    List<CarProjection> findCars();

}
