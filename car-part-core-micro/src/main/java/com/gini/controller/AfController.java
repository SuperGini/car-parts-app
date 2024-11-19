package com.gini.controller;

import com.gini.api.AfPartApi;
import com.gini.dto.AfPartRequest;
import com.gini.dto.AfPartResponse;
import com.gini.repository.filters.CarFilter;
import com.gini.service.AfPartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AfController implements AfPartApi {

    private final AfPartService afPartService;

    @GetMapping("/af-part")
    public void afParts(@RequestBody CarFilter carFilter) {
        afPartService.getAfPart(carFilter);
    }

    @Override
    public ResponseEntity<AfPartResponse> createAfPart(AfPartRequest afPartRequest) {
        var afPartresponse = afPartService.saveAfPart(afPartRequest);
        return ResponseEntity.ok(afPartresponse);
    }
}
