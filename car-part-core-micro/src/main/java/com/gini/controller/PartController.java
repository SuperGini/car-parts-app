package com.gini.controller;

import com.gini.api.PartApi;
import com.gini.dto.PartManufacturerRequest;
import com.gini.dto.PartManufacturerResponse;
import com.gini.dto.PartRequest;
import com.gini.dto.PartResponse;
import com.gini.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PartController implements PartApi {

    private final PartService partService;

    @Override
    public ResponseEntity<PartResponse> createPart(PartRequest partRequest) {
        return null;
    }

    @Override
    public ResponseEntity<PartManufacturerResponse> createPartManufacturer(PartManufacturerRequest partManufacturerRequest) {
        return null;
    }

    @Override
    public ResponseEntity<PartResponse> findPartById(UUID id) {
        return null;
    }
}
