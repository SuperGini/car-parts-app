package com.gini.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"",
            "/search/parts",
            "/",
    })
    public String index() {
        return "forward:/index.html";
    }


}
