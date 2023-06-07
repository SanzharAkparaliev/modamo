package com.madoma.service;


import com.madoma.entity.Category;
import com.madoma.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void saveCategory(Category categoryModel){
        Category category = categoryModel.getId() == null ? new Category() : categoryRepository.getById(categoryModel.getId());
        category.setName(categoryModel.getName());

        categoryRepository.save(category);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public void  deleteCategory(Long id){
        categoryRepository.deleteById(id);
    }

    public Category getCategory(Long id){
        return categoryRepository.getById(id);
    }
}
