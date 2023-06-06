package com.madoma.service;

import com.madoma.entity.Day;
import com.madoma.entity.Specialist;

import java.util.List;
import java.util.Optional;

public interface DayService {
    List<Day> getAllDays();
    Optional<Day> getDayById(Long id);
    Day getDayByName(String name);
    List<Day> getDaysBySpecialist(Specialist specialist);
    List<Day> getDaysBySpecialistAndFree(Specialist specialist);
}
