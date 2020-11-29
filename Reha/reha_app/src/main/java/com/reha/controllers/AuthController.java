package com.reha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String auth() {
        return "auth/Login";
    }


    @GetMapping("/")
    public String home() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "auth/Welcome";
    }


}
