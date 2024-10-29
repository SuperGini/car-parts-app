package com.gini.repository;

import com.gini.model.CarManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarManufacturerRepository extends JpaRepository<CarManufacturer, String> {
}
