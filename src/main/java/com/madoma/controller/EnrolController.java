package com.madoma.controller;

import com.madoma.entity.*;
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
        System.out.println("email " + clientEmail);
        int otp = random.nextInt(999999);

        //write code for send otp to email...
        String subject = "Modamo";
        String message = "OTP:" + otp + "\n\n";
        String to = (String) session.getAttribute("email");
        System.out.println(to);
        boolean flag = emailService.sendEmail(subject,message,to);
        session.setAttribute("email",clientEmail);

        if(flag){
            session.setAttribute("myotp",otp);
            session.setAttribute("email",clientEmail);
            session.setAttribute("client",clientName);
            session.setAttribute("email",clientEmail);
            session.setAttribute("phone",clientphone);
            session.setAttribute("master",specialist);
            session.setAttribute("day",day);
            session.setAttribute("time",time);

            model.addAttribute("user",new Client());
            model.addAttribute("title", "OTP текшерүү");
            return "verify_otp";
        }else {
            session.setAttribute("message","Электрондук почтаңыздын идентификаторун текшериңиз !!");
            model.addAttribute("user",new Client());
            model.addAttribute("title", "OTP текшерүү");
            return "verify_otp";
        }

    }


    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") int otp,HttpSession session,Model model){
        System.out.println("WORKKKKK");
        int myOtp = (int)session.getAttribute("myotp");
        String email = (String)session.getAttribute("email");

        if(myOtp==otp){
            //password change from
           List< Client> user = clientService.getUserByUsername(email);
            if(user==null){
                //send error message

                session.setAttribute("message","Мындай электрондук почта менен катталынган эмес!!");
                model.addAttribute("user",new Client());
                model.addAttribute("title", "OTP текшерүү");

                return "verify_otp";
            }else{

                eventService.saveEvent((String) session.getAttribute("day"),
                        (String) session.getAttribute("time"),
                        (String) session.getAttribute("client"),
                        (String) session.getAttribute("master"),
                        (String) session.getAttribute("phone"));
                clientService.createCrops((String) session.getAttribute("client"),
                        (String) session.getAttribute("email"),
                        (String) session.getAttribute("phone"),
                        (String) session.getAttribute("master"),
                        (String) session.getAttribute("day"),
                        (String) session.getAttribute("time"));

                model.addAttribute("user",new Client());
                String subject = "Modamo";
                String message = "Салон красоты Modamo\n\n" +
                        "Вы успешно записаны, " + (String) session.getAttribute("client") + "\n\n" +
                        "Благодарим вас за запись в наш салон красоты Modamo. Мы с нетерпением ждем вашего визита.\n\n" +
                        "Если у вас возникнут вопросы или вам потребуется изменить вашу запись, не стесняйтесь связаться с нами. \n\n"
                        +"наши контакты: +996 (0ххх)-хх-хх-хх";
                String to = (String) session.getAttribute("email");
                boolean flag = emailService.sendEmail(subject, message, to);
                return "redirect:/";
        }}
        else {
            session.setAttribute("message","Сиз туура эмес otp киргиздиңиз !!");
            return "verify_otp";
        }
    }
}
