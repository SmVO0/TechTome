package com.SVO.TechTome.repositories;

import com.SVO.TechTome.models.Category;
import com.SVO.TechTome.models.StoreItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoreItemRepository extends JpaRepository<StoreItem, UUID> {

    List<StoreItem> getAllByCategory(Category category);

    Page<StoreItem> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable);
}
