package com.madoma.service.impl;

import com.madoma.entity.Day;
import com.madoma.entity.Event;
import com.madoma.entity.Specialist;
import com.madoma.repository.EventRepository;
import com.madoma.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private DayService dayService;

    @Autowired
    private SpecialistService specialistService;


    static  final List<String> clocks = new ArrayList<>(){{
        add("9:30");
        add("11:00");
        add("12:30");
        add("14:00");
        add("15:30");
        add("17:00");
        add("18:30");
    }};


    public void saveEvent(String date, String clock, String teacherName,String master,String phone){
        try {
            System.out.println("Master");
            Day day = dayService.getDay(date);
            Optional<Specialist> specialistById = Optional.ofNullable(specialistService.getSpecialistByName(master));
            Event eventModel = eventRepository.findByDayAndClockAndSpecialist(day,clock,specialistById.get());
            if(eventModel.getTeacherName() == null){
                eventModel.setTeacherName(teacherName);
                eventModel.setSpecialist(specialistById.get());
                eventModel.setPhone(phone);
                eventRepository.save(eventModel);
            }
        }catch (RuntimeException exception){

        }
    }

    public Page<Event> getAllEvent(int pageNumber,int masterId){
        Optional<Specialist> specialistById = specialistService.getSpecialistById((long) masterId);
        Pageable pageable = PageRequest.of(pageNumber-1,35);
        return eventRepository.findBySpecialist(pageable,specialistById.get());
    }

    public void createEventTable(Integer masterId){
        List<Event> bySpecialist = eventRepository.findBySpecialist(specialistService.getSpecialistById(Long.valueOf(masterId)).get());
        System.out.println("List "+ bySpecialist);
        if(bySpecialist.isEmpty()){
            List<Day> allDay = dayService.getAllDay();
            Optional<Specialist> specialist = specialistService.getSpecialistById(Long.valueOf(masterId));

            for(Day day :allDay){
                for (String clock :clocks){
                    Event event = new Event();
                    event.setDay(day);
                    event.setClock(clock);
                    event.setSpecialist(specialist.get());
                    eventRepository.save(event);
                }
            }
        }
    }

    public void createOneEvent(Day day){
        for(String clock:clocks){
            Event event = new Event();
            event.setDay(day);
            event.setClock(clock);
            eventRepository.save(event);
        }

    }

    public  void deleteEvents(Day day){
        eventRepository.deleteAllByDay(day);
    }

    public List<Event> findAllEventList(){
        return eventRepository.findAll();
    }

    public void deleteEventsById(Long id){
        Optional<Event> event = eventRepository.findById(id);
        event.get().setTeacherName(null);
        event.get().setGruppa(null);
        eventRepository.save(event.get());
    }

    public List<Event> getByDayAndMaster(Day day,Specialist specialist){
        return eventRepository.findByDayAndSpecialistAndTeacherName(day,specialist,null);
    }

    public Event getByClockAndMaster(String clock, Long specialist, Long dayId){
        Optional<Specialist> specialist1 = specialistService.getSpecialistById(specialist);
        Day dayById = dayService.getDayById(dayId);
        return eventRepository.findByClockAndSpecialistAndDay(clock,specialist1.get(),dayById);
    }

    public Event getById(Long id){
        return eventRepository.getById(id);
    }
}

