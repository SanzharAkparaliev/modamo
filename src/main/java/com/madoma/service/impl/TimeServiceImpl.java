package com.madoma.service.impl;

import com.madoma.entity.Day;
import com.madoma.entity.Time;
import com.madoma.repository.TimeRepository;
import com.madoma.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeServiceImpl implements TimeService {
    @Autowired
    private TimeRepository timeRepository;

    @Override
    public List<Time> getTimeByDay(Day day) {
        return timeRepository.findByDay(day);
    }

    @Override
    public List<Time> getTimeByDayAndFree(Day day) {
        return timeRepository.findByDayAndFreeIsTrue(day);
    }

    @Override
    public Time getTimeByName(String timeName) {
        return timeRepository.findByTime(timeName);
    }

    @Override
    public Optional<Time> getTimeById(Long id) {
        return timeRepository.findById(id);
    }
}
