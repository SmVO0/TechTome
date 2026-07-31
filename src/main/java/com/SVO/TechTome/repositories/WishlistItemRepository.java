package com.SVO.TechTome.repositories;

import com.SVO.TechTome.models.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, UUID> {

    List<WishlistItem> findByUserId(UUID userId);

    Optional<WishlistItem> findByUserIdAndStoreItemId(UUID userId, UUID storeItemId);

    boolean existsByUserIdAndStoreItemId(UUID userId, UUID storeItemId);
}
