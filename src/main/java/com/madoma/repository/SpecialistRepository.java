package com.madoma.repository;

import com.madoma.entity.Category;
import com.madoma.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialistRepository extends JpaRepository<Specialist,Long> {
    List<Specialist> findByCategory(Category category);
    List<Specialist> findByCategoryAndFreeIsTrue(Category category);
    Specialist findByName(String name);
}
