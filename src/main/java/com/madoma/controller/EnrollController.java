package com.madoma.controller;

import com.madoma.entity.*;
import com.madoma.service.ClientService;
import com.madoma.service.DayService;
import com.madoma.service.SpecialistService;
import com.madoma.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enroll")
public class EnrollController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private DayService dayService;
    @Autowired
    private TimeService timeService;

    @GetMapping("/client/{id}")
    public String getEnrollPage(@PathVariable("id") Long id,
                                Model model){
        Time time = timeService.getTimeById(id).get();
        model.addAttribute("time", time);
        return "enroll";
    }

    @GetMapping("/category")
    public String getCategoryPage(){
        return "choose_category";
    }

    //**************************************************************************
    // for man
//    @GetMapping("/man/specialist")
//    public String getManSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.man);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    // for women
//    @GetMapping("/women/specialist")
//    public String getWomenSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.women);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    // for nogti
//    @GetMapping("/nogti/specialist")
//    public String getNogtiSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.nogti);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    // for brovi
//    @GetMapping("/brovi/specialist")
//    public String getBroviSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.brovi);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    // for makeup
//    @GetMapping("/makeup/specialist")
//    public String getMakeupSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.makeup);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    // for face
//    @GetMapping("/face/specialist")
//    public String getFaceSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.face);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    // for botox
//    @GetMapping("/botox/specialist")
//    public String getBotoxSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.botox);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    // for epilation
//    @GetMapping("/epilation/specialist")
//    public String getEpilationSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.epilation);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    // for sertificate
//    @GetMapping("/sertificate/specialist")
//    public String getSertificateSpecialist(Model model){
//        List<Specialist> specialists = specialistService.getSpecialistByCategoryAndFree(Category.sertificate);
//        model.addAttribute("specialists", specialists);
//        return "choose_specialist";
//    }

    //**************************************************************************

    @GetMapping("/specialist/{specialistId}/day")
    public String getDay(@PathVariable("specialistId") Long specialistId,
                         Model model){
        Specialist specialist = specialistService.getSpecialistById(specialistId).get();
        List<Day> days = dayService.getDaysBySpecialistAndFree(specialist);
        model.addAttribute("days", days);
        return "choose_day";
    }

    @GetMapping("/specialist/day/{dayId}/time")
    public String getTime(@PathVariable("dayId") Long dayId,
                          Model model){
        Day day = dayService.getDayById(dayId).get();
        List<Time> times = timeService.getTimeByDayAndFree(day);
        model.addAttribute("times", times);
        return "choose_time";
    }

    @PostMapping("/specialist")
    public String getSpecialistId(@RequestParam("specialist") String specialistName){
        Specialist specialist = specialistService.getSpecialistByName(specialistName);
        return "redirect:/enroll/specialist/" + specialist.getId() + "/day";
    }

    @PostMapping("/specialist/day")
    public String getDayId(@RequestParam("day") String dayName){
        Day day = dayService.getDayByName(dayName);
        return "redirect:/enroll/specialist/day/" + day.getId() + "/time";
    }

    @PostMapping("/specialist/day/time")
    public String getTimeId(@RequestParam("time") String timeName){
        Time time = timeService.getTimeByName(timeName);
        return "redirect:/enroll/client/" + time.getId();
    }

    @PostMapping("/save")
    public String saveEnroll(@RequestParam("name") String clientName,
                             @RequestParam("email") String clientEmail,
                             @RequestParam("phone") String clientphone,
                             @RequestParam("specialist") String specialist,
                             @RequestParam("day") String day,
                             @RequestParam("time") String time){
        clientService.createCrops(clientName,clientEmail,clientphone,specialist,day,time);
        return "redirect:/";
    }

    @PostMapping("/category")
    public String Category(@RequestParam("category") String category){
        return "redirect:/enroll/" + specialistService.getCategory(category) + "/specialist";
    }
}
