package com.SVO.TechTome.storeItem.service;

import com.SVO.TechTome.category.model.Category;
import com.SVO.TechTome.storeItem.model.StoreItem;
import com.SVO.TechTome.storeItem.repository.StoreItemRepository;
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
