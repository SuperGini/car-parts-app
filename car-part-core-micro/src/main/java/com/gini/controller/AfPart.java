package com.gini.controller;

import com.gini.service.AfPartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AfPart {

    private final AfPartService afPartService;


    @PostMapping("/af-part")
    public void savePart (@RequestBody AfRequest af) {

    afPartService.saveAfPart(af);


    }
}
