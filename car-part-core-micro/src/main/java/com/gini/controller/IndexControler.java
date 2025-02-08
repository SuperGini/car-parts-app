package com.gini.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
@Controller
public class IndexControler {

    //for this to work in Angular i redirect -> "" to main/cars
    //and in the .yaml file i tell it that the index.html is in the browser folder
    @GetMapping({"",
            "main/**",
            "/",
            "/micro-core/loginx",
            "/micro-core/main/**"
    })
    public String redirectToMainPage() {
        return "forward:/index.html";
    }

}

