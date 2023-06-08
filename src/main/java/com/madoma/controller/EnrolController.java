package com.madoma.controller;

import com.madoma.entity.Category;
import com.madoma.entity.Day;
import com.madoma.entity.Event;
import com.madoma.entity.Specialist;
import com.madoma.service.CategoryService;
import com.madoma.service.ClientService;
import com.madoma.service.SpecialistService;
import com.madoma.service.impl.DayService;
import com.madoma.service.impl.EmailService;
import com.madoma.service.impl.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/enroll")
public class EnrolController {
    Random random = new Random(1000);
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private DayService dayService;
    @Autowired
    private EventService eventService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmailService emailService;
    @GetMapping("/category")
    public String getCategoryPage(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        model.addAttribute("title","Categories");
        return "choose_category";
    }

    @PostMapping("/category")
    public String Category(@RequestParam("category") Long category){
        Category category1 = categoryService.getCategory(category);
        return "redirect:/enroll/" + category1.getName() + "/specialist";
    }

    @GetMapping("{category}/specialist")
    public String getManSpecialist(Model model, @PathVariable("category") String category){
        List<Specialist> specialists = specialistService.findByCategory(category);
        model.addAttribute("specialists", specialists);
        return "choose_specialist";
    }

    @PostMapping("/specialist")
    public String getSpecialistId(@RequestParam("specialist") String specialistName){
        Specialist specialist = specialistService.getSpecialistByName(specialistName);
        return "redirect:/enroll/specialist/" + specialist.getId() + "/day";
    }

    @GetMapping("/specialist/{specialistId}/day")
    public String getDay(@PathVariable("specialistId") Long specialistId,
                         Model model){
        Specialist specialist = specialistService.getSpecialistById(specialistId).get();
        List<Day> days = dayService.getAllDay();
        model.addAttribute("days", days);
        model.addAttribute("masterId",specialistId);
        return "choose_day";
    }

    @PostMapping("/specialist/day/{masterId}")
    public String getDayId(@PathVariable("masterId") Long id,
            @RequestParam("day") String dayName){
        Day day = dayService.getDayByName(dayName);
        return "redirect:/enroll/specialist/day/" + day.getId() + "/time/" + id;
    }
    @GetMapping("/specialist/day/{dayId}/time/{id}")
    public String getTime(@PathVariable("dayId") Long dayId,@PathVariable("id") Long masterId,
                          Model model){
        Day day = dayService.getDayById(dayId);
        Optional<Specialist> specialist = specialistService.getSpecialistById(masterId);
        List<Event> byDayAndMaster = eventService.getByDayAndMaster(day, specialist.get());
        model.addAttribute("times", byDayAndMaster);
        model.addAttribute("masterId",masterId);
        model.addAttribute("dayId",dayId);
        return "choose_time";
    }

    @PostMapping("/specialist/day/{dayId}/time/{masterId}")
    public String getTimeId(@PathVariable("masterId") Long masterid,
                            @PathVariable("dayId") Long dayId,
                            @RequestParam("time") String timeName,
                            Model model){
        model.addAttribute("masterId",masterid);
        model.addAttribute("dayId",dayId);
        model.addAttribute("time",timeName);

        Event time = eventService.getByClockAndMaster(timeName,masterid,dayId);
        return "redirect:/enroll/client/master/"+masterid+"/day/"+dayId+"/time/"+time.getId();
    }

    @GetMapping("/client/master/{masterId}/day/{dayId}/time/{timeId}")
    public String getEnrollPage(@PathVariable("masterId") Long masterId,
                                @PathVariable("dayId") Long dayId,
                                @PathVariable("timeId") Long timeId,
                                Model model){

        Event byId = eventService.getById(timeId);
        model.addAttribute("event",byId);
        return "enroll";
    }

    @PostMapping("/save")
    public String saveEnroll(@RequestParam("name") String clientName,
                             @RequestParam("email") String clientEmail,
                             @RequestParam("phone") String clientphone,
                             @RequestParam("specialist") String specialist,
                             @RequestParam("day") String day,
                             @RequestParam("time") String time,
                             Model model, HttpSession session){
        int otp = random.nextInt(999999);

        //write code for send otp to email...
        String subject = "Modamo";
        String message = ""
                + "<div style='border:1px solid #e2e2e2;padding:20px'>"
                +"<h1>"+"Саламатсызбы?"+clientName+"</h1>"
                +"<h3>"
                + "Сиз ийгиликтуу жазылдыныз!" +
                "</h3>"
                +"</div>";
        String to = clientEmail;
        boolean flag = emailService.sendEmail(subject,message,to);
        if(flag) {
            Specialist byName = specialistService.getByName(specialist);
            eventService.saveEvent(day, time, clientName, Math.toIntExact(byName.getId()));
            clientService.createCrops(clientName, clientEmail, clientphone, specialist, day, time);
        }else {
            session.setAttribute("message","Электрондук почтаңыздын идентификаторун текшериңиз !!");
        }
        return "redirect:/";
    }
}
