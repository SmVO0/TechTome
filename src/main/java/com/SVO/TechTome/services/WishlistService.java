package com.SVO.TechTome.services;

import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.WishlistItem;
import com.SVO.TechTome.repositories.StoreItemRepository;
import com.SVO.TechTome.repositories.WishlistItemRepository;
import com.SVO.TechTome.web.exception.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.SVO.TechTome.constants.ExceptionMessages.STORE_ITEM_NOT_FOUND;

@Service
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final StoreItemRepository storeItemRepository;
    private final UserService userService;

    public WishlistService(WishlistItemRepository wishlistItemRepository,
                           StoreItemRepository storeItemRepository,
                           UserService userService) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.storeItemRepository = storeItemRepository;
        this.userService = userService;
    }

    @Transactional
    public boolean toggle(UUID userId, UUID itemId) {
        if (wishlistItemRepository.existsByUserIdAndStoreItemId(userId, itemId)) {
            wishlistItemRepository.findByUserIdAndStoreItemId(userId, itemId)
                    .ifPresent(wishlistItemRepository::delete);
            return false;
        }

        User user = userService.getById(userId);
        StoreItem item = storeItemRepository.findById(itemId)
                .orElseThrow(() -> new DomainException(STORE_ITEM_NOT_FOUND));

        wishlistItemRepository.save(WishlistItem.builder()
                .user(user)
                .storeItem(item)
                .build());
        return true;
    }

    public List<WishlistItem> getWishlist(UUID userId) {
        return wishlistItemRepository.findByUserId(userId);
    }

    public boolean isWishlisted(UUID userId, UUID itemId) {
        return wishlistItemRepository.existsByUserIdAndStoreItemId(userId, itemId);
    }
}
