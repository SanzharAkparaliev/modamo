package com.madoma.service.impl;

import com.madoma.entity.Day;
import com.madoma.entity.Specialist;
import com.madoma.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DayService {
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private EventService eventService;


    public Day getDay(String day){
        return  dayRepository.findByDate(day);
    }

    public List<Day> getAllDay(){
        return dayRepository.findAll();
    }

    public void  createTable(Integer masterId){

        LocalDate localDate = LocalDate.now();
        Day lastDay = dayRepository.findByDate(String.valueOf(localDate.plusDays(29)));
        Day penultimate = dayRepository.findByDate(String.valueOf(localDate.plusDays(28)));


        if(lastDay == null){
            eventService.createEventTable(masterId);

            if( penultimate != null){
                Day dayRight = new Day();
                dayRight.setDate(String.valueOf(localDate.plusDays(29)));
                createDayAndEvent(dayRight);
                eventService.createEventTable(masterId);


                Day dayLeft = new Day();
                dayLeft.setDate(String.valueOf(localDate.plusDays(-1)));
                Day byDate = dayRepository.findByDate(dayLeft.getDate());
                eventService.deleteEvents(byDate);
                dayRepository.deleteByDate(byDate.getDate());
                eventService.createEventTable(masterId);


            }else {
                for(int i=1; i < 31; i++){
                    Day dayNow = new Day();
                    dayNow.setDate(String.valueOf(localDate.plusDays(i-1)));
                    dayRepository.save(dayNow);
                }
                eventService.createEventTable(masterId);
            }
        }else {
            eventService.createEventTable(masterId);
        }
        eventService.createEventTable(masterId);


    }

    public Page<Day> findAllDays(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber-1,5);
        return dayRepository.findAll(pageable);
    }

    public void createDayAndEvent(Day dayNow){
        dayRepository.save(dayNow);
        eventService.createOneEvent(dayNow);
    }


    public Day getDayByName(String dayName) {
        return dayRepository.findByDate(dayName);
    }

    public Day getDayById(Long dayId) {
        return dayRepository.findById(dayId).get();
    }
}