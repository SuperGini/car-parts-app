package com.gini.repository;

import com.gini.model.PartManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParManufacturerRepository extends JpaRepository<PartManufacturer, String> {

    @Query("SELECT pm FROM PartManufacturer pm")
    List<PartManufacturer> getAllPartManufacturers();
}
