package com.madoma.controller;


import com.madoma.entity.Day;
import com.madoma.entity.Event;
import com.madoma.entity.Specialist;
import com.madoma.service.SpecialistService;
import com.madoma.service.impl.DayService;
import com.madoma.service.impl.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class MasterController {

    @Autowired
    private DayService dayService;
    @Autowired
    private EventService eventService;
    @Autowired
    private SpecialistService specialistService;


    @GetMapping("/master/calendar/{id}")
    public String getCalendarPage(@PathVariable("id") Integer masterId,Model model, HttpSession session){
        dayService.createTable(masterId);
        return listByPage(model,1,session,masterId);
    }

    @GetMapping("/page/{pageNumber}/{id}")
    public String listByPage(Model model, @PathVariable("pageNumber") int currentPage,
                             HttpSession httpSession,
                             @PathVariable("id") Integer masterId){
        httpSession.setAttribute("pageNumber",currentPage);
        Page<Day> dayList = dayService.findAllDays(currentPage);
        LocalDate dt = LocalDate.parse(LocalDate.now().toString());
        Long totalItems = dayList.getTotalElements();
        int totalPages = dayList.getTotalPages();
        Page<Event> events = eventService.getAllEvent(currentPage,masterId);
        model.addAttribute("events",events);
        model.addAttribute("title","IWEX");
        model.addAttribute("month",dt.getMonth());
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("days",dayList);
        model.addAttribute("title","Расписание");
        model.addAttribute("masterId",masterId);
        return "admin/calendar";
    }

    @PostMapping("/saveEvent/master/{id}")
    public String saveEvent(@RequestParam("teacherName") String teacherName,
                            @RequestParam("clock") String clock,
                            @RequestParam("date") String date,
                            @RequestParam("phone") String phone,
                            @PathVariable("id") Integer masterId){
        System.out.println("id " + masterId);
        Optional<Specialist> specialistById = specialistService.getSpecialistById(Long.valueOf(masterId));

        eventService.saveEvent(date,clock,teacherName,specialistById.get().getName(),phone);
        return "redirect:/admin/master/calendar/" + masterId;
    }





}
