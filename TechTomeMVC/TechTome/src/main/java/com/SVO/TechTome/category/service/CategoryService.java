package com.SVO.TechTome.category.service;

import com.SVO.TechTome.category.model.Category;
import com.SVO.TechTome.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategory(String name) {

        return categoryRepository.findByName(name);
    }
}
