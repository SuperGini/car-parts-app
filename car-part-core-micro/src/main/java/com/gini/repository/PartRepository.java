package com.gini.repository;

import com.gini.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


public interface PartRepository extends JpaRepository<Part, String>, JpaSpecificationExecutor<Part> {
}
