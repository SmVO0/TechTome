package com.SVO.TechTome.services;

import com.SVO.TechTome.models.ShoppingCart;
import com.SVO.TechTome.models.ShoppingCartItem;
import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.repositories.ShoppingCartItemRepository;
import com.SVO.TechTome.repositories.ShoppingCartRepository;
import com.SVO.TechTome.repositories.StoreItemRepository;
import com.SVO.TechTome.web.exception.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final StoreItemRepository storeItemRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ShoppingCartItemRepository shoppingCartItemRepository,
                               StoreItemRepository storeItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.storeItemRepository = storeItemRepository;
    }

    @Transactional
    public void addItem(UUID cartId, UUID storeItemId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new DomainException("Shopping cart not found."));
        StoreItem item = storeItemRepository.findById(storeItemId)
                .orElseThrow(() -> new DomainException("Item not found."));

        ShoppingCartItem cartItem = shoppingCartItemRepository
                .findByCartAndStoreItem(cart, item)
                .orElseGet(() -> ShoppingCartItem.builder()
                        .cart(cart)
                        .storeItem(item)
                        .quantity(0)
                        .build());

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        shoppingCartItemRepository.save(cartItem);
        recalculateTotalPrice(cart);
    }

    @Transactional
    public void removeItem(UUID cartId, UUID storeItemId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new DomainException("Shopping cart not found."));
        StoreItem item = storeItemRepository.findById(storeItemId)
                .orElseThrow(() -> new DomainException("Item not found."));

        shoppingCartItemRepository.findByCartAndStoreItem(cart, item).ifPresent(cartItem -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                shoppingCartItemRepository.save(cartItem);
            } else {
                shoppingCartItemRepository.delete(cartItem);
            }
        });

        recalculateTotalPrice(cart);
    }

    public List<ShoppingCartItem> getItems(UUID cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new DomainException("Shopping cart not found."));
        return shoppingCartItemRepository.findByCart(cart);
    }

    private void recalculateTotalPrice(ShoppingCart cart) {
        List<ShoppingCartItem> items = shoppingCartItemRepository.findByCart(cart);
        BigDecimal total = items.stream()
                .map(ci -> ci.getStoreItem().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
        shoppingCartRepository.save(cart);
    }
}
