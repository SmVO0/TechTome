package com.SVO.TechTome.services;

import com.SVO.TechTome.models.Category;
import com.SVO.TechTome.repositories.CategoryRepository;
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
