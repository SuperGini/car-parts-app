package com.gini.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexControler {


    @GetMapping("/main/**")
    public String redirectToMainPage() {
        return "forward:/index.html";
    }


}
