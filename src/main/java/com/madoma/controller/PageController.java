package com.madoma.controller;

import com.madoma.entity.Category;
import com.madoma.entity.Price;
import com.madoma.service.CategoryService;
import com.madoma.service.impl.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PriceService priceService;

    @GetMapping("/about")
    public String getAboutPage(Model model){
        model.addAttribute("title","About Us");
        return "about";
    }

    @GetMapping("/women")
    public String getWomenPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | Women zal");
        return "women";
    }

    @GetMapping("/man")
    public String getManPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | forman");
        return "man";
    }

    @GetMapping("/nogti")
    public String getNogtiPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | nogti");
        return "nogti";
    }

    @GetMapping("/brovi")
    public String getBroviPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | brovi");
        return "brovi";
    }

    @GetMapping("/makeup")
    public String getMakeUpPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | makeup");
        return "makeup";
    }

    @GetMapping("/face")
    public String getFacePage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | massage");
        return "face";
    }

    @GetMapping("/botox")
    public String getBotoxPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | botox");
        return "botox";
    }

    @GetMapping("/epilation")
    public String getEpilationPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | epilation ");
        return "epilation";
    }

    @GetMapping("/sertificate")
    public String getSertificatePage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | sertificate");
        return "sertificate";
    }

    @GetMapping("/price")
    public String getPricePage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | Prices ");

        List<Price> price = priceService.getAll();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("prices",price);
        return "price";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | Contact");
        return "contact";
    }

    @GetMapping("/services")
    public String getServicePage(Model model){
        model.addAttribute("title","Modamo Beauty Studio | services ");
        return "services";
    }
}
