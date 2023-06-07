package com.madoma.service;

import com.madoma.entity.Category;
import com.madoma.entity.Specialist;

import java.util.List;
import java.util.Optional;

public interface SpecialistService {

    List<Specialist> getAllSpecialist();
    Optional<Specialist> getSpecialistById(Long id);
    List<Specialist> getSpecialistByCategory(Category category);
    List<Specialist> getSpecialistByCategoryAndFree(Category category);
    Specialist getSpecialistByName(String name);
    Category getCategory(String categoryName);

    void saveMaster(Specialist specialist);
    void  deleteMaster(Long id);
}
