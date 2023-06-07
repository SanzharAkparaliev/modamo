package com.madoma.service.impl;

import com.madoma.entity.Category;
import com.madoma.entity.Specialist;
import com.madoma.repository.SpecialistRepository;
import com.madoma.service.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialistServiceImpl implements SpecialistService {
    @Autowired
    private SpecialistRepository specialistRepository;

    @Override
    public List<Specialist> getAllSpecialist() {
        return specialistRepository.findAll();
    }

    @Override
    public Optional<Specialist> getSpecialistById(Long id) {
        return specialistRepository.findById(id);
    }

    @Override
    public List<Specialist> getSpecialistByCategory(Category category) {
        return specialistRepository.findByCategory(category);
    }

    @Override
    public List<Specialist> getSpecialistByCategoryAndFree(Category category) {
        return specialistRepository.findByCategoryAndFreeIsTrue(category);
    }

    @Override
    public Specialist getSpecialistByName(String name) {
        return specialistRepository.findByName(name);
    }

    @Override
    public Category getCategory(String categoryName) {
//        switch (categoryName){
//            case "Женский зал":
//                return Category.women;
//            case "Мужской зал":
//                return Category.man;
//            case "Ногтевой сервис":
//                return Category.nogti;
//            case "Брови/Ресницы":
//                return Category.brovi;
//            case "Make Up":
//                return Category.makeup;
//            case "Массаж":
//                return Category.face;
//            case "Ботокс":
//                return Category.botox;
//            case "Эпиляция":
//                return Category.epilation;
//            case "Подарочные сертификаты":
//                return Category.sertificate;
//            default:
//                return null;
//        }

    return  null;}
}
