package com.madoma.service.impl;

import com.madoma.entity.Day;
import com.madoma.entity.Specialist;
import com.madoma.repository.DayRepository;
import com.madoma.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DayServiceImpl implements DayService {
    @Autowired
    private DayRepository dayRepository;

    @Override
    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }

    @Override
    public Optional<Day> getDayById(Long id) {
        return dayRepository.findById(id);
    }

    @Override
    public Day getDayByName(String name) {
        return dayRepository.findByDay(name);
    }

    @Override
    public List<Day> getDaysBySpecialist(Specialist specialist) {
        return dayRepository.findBySpecialist(specialist);
    }

    @Override
    public List<Day> getDaysBySpecialistAndFree(Specialist specialist) {
        return dayRepository.findBySpecialistAndFreeIsTrue(specialist);
    }
}
