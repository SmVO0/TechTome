package com.SVO.TechTome.services;

import com.SVO.TechTome.models.Category;
import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.repositories.StoreItemRepository;
import com.SVO.TechTome.web.exception.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.SVO.TechTome.constants.ExceptionMessages.STORE_ITEM_NOT_FOUND;

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

    public Page<StoreItem> getStoreItemsByCategory(Category category, int page) {
        PageRequest pageable = PageRequest.of(page, 12, Sort.by("name").ascending());
        return storeItemRepository.findByCategory(category, pageable);
    }

    public StoreItem getById(UUID id) {
        return storeItemRepository.findById(id)
                .orElseThrow(() -> new DomainException(STORE_ITEM_NOT_FOUND));
    }

    public List<StoreItem> getAllItems() {
        return storeItemRepository.findAll();
    }

    public StoreItem save(StoreItem item) {
        return storeItemRepository.save(item);
    }

    public void deleteById(UUID id) {
        storeItemRepository.deleteById(id);
    }

    public List<StoreItem> getFeaturedItems() {
        return storeItemRepository.findTop6ByFeaturedTrueOrderByNameAsc();
    }
}
