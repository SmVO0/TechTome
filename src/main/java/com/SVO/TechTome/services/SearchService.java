package com.SVO.TechTome.services;

import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.repositories.StoreItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private static final int PAGE_SIZE = 12;

    private final StoreItemRepository storeItemRepository;

    public SearchService(StoreItemRepository storeItemRepository) {
        this.storeItemRepository = storeItemRepository;
    }

    public Page<StoreItem> search(String query, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("name"));
        if (query == null || query.isBlank()) {
            return storeItemRepository.findAll(pageable);
        }
        return storeItemRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, pageable);
    }
}
