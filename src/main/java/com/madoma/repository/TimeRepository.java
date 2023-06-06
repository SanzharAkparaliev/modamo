package com.madoma.repository;

import com.madoma.entity.Day;
import com.madoma.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRepository extends JpaRepository<Time,Long> {
    List<Time> findByDay(Day day);
    List<Time> findByDayAndFreeIsTrue(Day day);
    Time findByTime(String time);
}
