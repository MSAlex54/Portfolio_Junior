package ru.my_prtfolio.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/")
@Controller
public class HomeController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getEmptyPage(){
        return "home";
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String getHomePage(){
        return "home";
    }
}
