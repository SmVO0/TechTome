package com.SVO.TechTome.services;

import com.SVO.TechTome.models.Category;
import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.repositories.StoreItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreItemService {

    private final StoreItemRepository storeItemRepository;

    @Autowired
    public StoreItemService(StoreItemRepository storeItemRepository) {
        this.storeItemRepository = storeItemRepository;
    }

    public List<StoreItem> getStoreItemsByCategory(Category category) {
        return storeItemRepository.getAllByCategory(category);
    }

}
