package com.madoma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String getIndexPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio");

        return "index";
    }
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
}
