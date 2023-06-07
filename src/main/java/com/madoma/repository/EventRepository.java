package com.madoma.repository;

import com.madoma.entity.Day;
import com.madoma.entity.Event;
import com.madoma.entity.Specialist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    public Event findByDayAndClockAndSpecialist(Day day, String clock, Specialist specialist);
    Page<Event> findAll(Pageable pageable);
    public void deleteAllByDay(Day day);

    Page<Event> findBySpecialist(Pageable pageable,Specialist specialist);
    List<Event> findBySpecialist(Specialist specialist);

    List<Event> findByDayAndSpecialistAndTeacherName(Day day,Specialist specialist,String tc);
    Event findByClockAndSpecialistAndDay(String clo,Specialist specialist,Day day);
}
