package com.gini.controller;

import com.gini.api.PartApi;
import com.gini.dto.PartManufacturerRequest;
import com.gini.dto.PartManufacturerResponse;
import com.gini.dto.PartRequest;
import com.gini.dto.PartResponse;
import com.gini.model.Part;
import com.gini.repository.filters.PartFilter;
import com.gini.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PartController implements PartApi {

    private final PartService partService;

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

    @GetMapping("/part/filter")
    public List<Part> finAllPartsWithFilter (@RequestBody PartFilter partFilter) {

       return partService.getAllPartsFiltered(partFilter);

    }
}
