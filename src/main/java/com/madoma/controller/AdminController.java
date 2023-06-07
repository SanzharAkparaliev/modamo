package com.madoma.controller;


import com.madoma.entity.Category;
import com.madoma.entity.Specialist;
import com.madoma.service.CategoryService;
import com.madoma.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String getAdminPage(Model model ){
        model.addAttribute("title","Modamo Admin Panel");
        return "admin/index";
    }

   @GetMapping("/specialist")
    public String getSpecialistPage(Model model){
       List<Category> categories = categoryService.findAll();
       model.addAttribute("categories",categories);
       List<Specialist> allSpecialist = specialistService.getAllSpecialist();
       model.addAttribute("title","Modamo Specialist");
       model.addAttribute("masters",allSpecialist);
        return "/admin/specialist";
   }

   @GetMapping("/category")
    public String getCategoryPage(Model model){
       List<Category> categories = categoryService.findAll();
       model.addAttribute("categories",categories);
       model.addAttribute("title","Categories");
        return "/admin/category";
   }

    @PostMapping("/saveCategory")
    public String saveCategory(@RequestParam("category") String categoryName){
        Category category = new Category();
        category.setName(categoryName);

        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@RequestParam("category") String categoryName,@RequestParam("id") Long id){
        Category category = new Category();
        category.setName(categoryName);
        category.setId(id);

        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }

    @PostMapping("/saveMaster")
    public String saveMaster(@ModelAttribute Specialist specialist){
        specialistService.saveMaster(specialist);
        return "redirect:/admin/specialist";
    }


    @GetMapping("/master/delete/{id}")
    public String deleteMaster(@PathVariable("id") Long id){
        specialistService.deleteMaster(id);
        return "redirect:/admin/specialist";
    }

    @PostMapping("/updateMaster")
    public String updateMaster(@ModelAttribute Specialist specialist){
        System.out.println("id" + specialist.getId());
        specialistService.saveMaster(specialist);
        return "redirect:/admin/specialist";
    }
}
