package com.SVO.TechTome.storeItem.repository;

import com.SVO.TechTome.category.model.Category;
import com.SVO.TechTome.storeItem.model.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoreItemRepository extends JpaRepository<StoreItem, UUID> {

    List<StoreItem> getAllByCategory(Category category);
}
