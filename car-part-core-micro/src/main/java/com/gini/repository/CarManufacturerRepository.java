package com.gini.repository;

import com.gini.model.CarManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarManufacturerRepository extends JpaRepository<CarManufacturer, String> {


    @Query("""
                SELECT cm FROM CarManufacturer cm
            """)
    List<CarManufacturer> finAllCarManufacturers();
}
