package com.gini.controller;

import com.gini.api.PartApi;
import com.gini.dto.CarFilterResponse;
import com.gini.dto.PartFilterRequest;
import com.gini.dto.PartManufacturerRequest;
import com.gini.dto.PartManufacturerResponse;
import com.gini.dto.PartRequest;
import com.gini.dto.PartResponse;
import com.gini.dto.PartResponse2;
import com.gini.dto.PartResponse2Wrapper;
import com.gini.model.Part;
import com.gini.repository.filters.PartFilter;
import com.gini.service.CarService;
import com.gini.service.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PartController implements PartApi {

    private final PartService partService;
    private final CarService carService;

    @Override
    public ResponseEntity<PartResponse> createPart(PartRequest partRequest) {
        var response = partService.save(partRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PartManufacturerResponse> createPartManufacturer(PartManufacturerRequest partManufacturerRequest) {
        var response = partService.save(partManufacturerRequest);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PartResponse> findPartById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<PartResponse2> findPartByPartNumber(String partNumber) {
        var response = partService.findPartByPartNumber2(partNumber);
        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<List<PartManufacturerResponse>> getAllPartManufacturers() {
        return ResponseEntity.ok(partService.getAllPartManufacturers());
    }

    @Override
    public ResponseEntity<List<CarFilterResponse>> getPartsForCarPaginated(PartFilterRequest partFilterRequest) {
        log.info("entering paginated controller");
        var x = carService.getAllPartsWithSpecificationPaginationAndSorting2(partFilterRequest);
        return ResponseEntity.ok(x);
    }

    @Override
    public ResponseEntity<PartResponse2Wrapper> getPartsPaginatedWithFilter(PartFilterRequest partFilterRequest) {
        var response = partService.findAllPartsPaginatedWithFilter(partFilterRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/part/filter")
    public List<Part> finAllPartsWithFilter(@RequestBody PartFilter partFilter) {

        partService.criteriabuilder(partFilter);

        return null;

    }

}
