package com.gini.repository;

import com.gini.model.PartManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParManufacturerRepository extends JpaRepository<PartManufacturer, String> {
}
