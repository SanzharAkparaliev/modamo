package com.madoma.repository;

import com.madoma.entity.Day;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DayRepository extends JpaRepository<Day,Long> {
    @Query("select d from Day d where d.date = :date")
    public Day findByDate(@Param("date") String date);
    Page<Day> findAll(Pageable pageable);
//    @Query("DELETE FROM Day d WHERE d.date = :date")
//    void deleteByDate(@Param("date") String date);


    void deleteByDate(String date);
}
