package com.gini.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/loginx")
    public String loginx() {
        return "login/loginPage";
    }
}
