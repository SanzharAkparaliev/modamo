package com.madoma.service;

import com.madoma.entity.Day;
import com.madoma.entity.Time;

import java.util.List;
import java.util.Optional;

public interface TimeService {
    List<Time> getTimeByDay(Day day);
    List<Time> getTimeByDayAndFree(Day day);
    Time getTimeByName(String timeName);
    Optional<Time> getTimeById(Long id);
}
