package com.gini.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestXssController {

    @PostMapping("/test")
    public String test(@RequestBody Part part, @RequestParam String xxx) {

        System.out.println(part);

        return xxx;

    }




}
