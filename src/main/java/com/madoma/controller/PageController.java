package com.madoma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/about")
    public String getAboutPage(Model model){
        model.addAttribute("title","About Us");
        return "about";
    }
}
