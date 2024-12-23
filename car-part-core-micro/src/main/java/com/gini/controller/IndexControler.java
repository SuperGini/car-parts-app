package com.gini.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControler {

    //for this to work in Angular i redirect -> "" to main/cars
    //and in the .yaml file i tell it that the index.html is in the browser folder
    @GetMapping({"", "main/**", "/"})
    public String redirectToMainPage() {
        return "forward:/index.html";
    }

}

