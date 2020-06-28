package ru.my_prtfolio.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/login")
@Controller
public class AuthController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }

}
