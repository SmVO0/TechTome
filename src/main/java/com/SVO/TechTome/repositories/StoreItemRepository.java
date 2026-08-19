package com.SVO.TechTome.repositories;

import com.SVO.TechTome.models.Category;
import com.SVO.TechTome.models.StoreItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface StoreItemRepository extends JpaRepository<StoreItem, UUID> {

    List<StoreItem> getAllByCategory(Category category);

    Page<StoreItem> findByCategory(Category category, Pageable pageable);

    Page<StoreItem> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable);

    List<StoreItem> findTop6ByFeaturedTrueOrderByNameAsc();

    @Query("SELECT s FROM StoreItem s WHERE s.category = :category " +
           "AND (:minPrice IS NULL OR s.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR s.price <= :maxPrice) " +
           "AND (:inStockOnly = false OR s.stock > 0)")
    Page<StoreItem> findByCategoryFiltered(
            @Param("category") Category category,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("inStockOnly") boolean inStockOnly,
            Pageable pageable);
}
