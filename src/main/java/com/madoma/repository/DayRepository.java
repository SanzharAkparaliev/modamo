package com.madoma.repository;

import com.madoma.entity.Day;
import com.madoma.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DayRepository extends JpaRepository<Day,Long> {
    List<Day> findBySpecialist(Specialist specialist);
    List<Day> findBySpecialistAndFreeIsTrue(Specialist specialist);
    Day findByDay(String day);
}
