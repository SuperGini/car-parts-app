package com.gini.controller;

import com.gini.repository.filters.CarFilter;
import com.gini.service.AfPartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AfController {

    private final AfPartService afPartService;

    @GetMapping("/af-part")
    public void afParts(@RequestBody CarFilter carFilter) {
        afPartService.getAfPart(carFilter);
    }

}
