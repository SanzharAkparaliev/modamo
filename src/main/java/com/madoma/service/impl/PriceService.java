package com.madoma.service.impl;


import com.madoma.entity.Category;
import com.madoma.entity.Price;
import com.madoma.repository.PriceRepository;
import com.madoma.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private CategoryService categoryService;
    public void savePrice(Price priceModel){
        Category category = categoryService.getCategory(priceModel.getCategory().getId());
        Price price = new Price();
        price.setSum(priceModel.getSum());
        price.setName(priceModel.getName());
        price.setCategory(category);
        priceRepository.save(price);
    }

    public List<Price> getAll(){
        return priceRepository.findAll();
    }
}
