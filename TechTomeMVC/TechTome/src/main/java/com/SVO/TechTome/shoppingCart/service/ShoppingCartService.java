package com.SVO.TechTome.shoppingCart.service;

import com.SVO.TechTome.shoppingCart.model.ShoppingCart;
import com.SVO.TechTome.shoppingCart.repository.ShoppingCartRepository;
import com.SVO.TechTome.storeItem.model.StoreItem;
import com.SVO.TechTome.storeItem.repository.StoreItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }


    private void addItemToCart(StoreItem storeItem, ShoppingCart shoppingCart) {
        List<StoreItem> items = shoppingCart.getItems();
        items.add(storeItem);
        shoppingCart.setItems(items);
        shoppingCartRepository.save(shoppingCart);
    }

    private void removeItemFromCart(StoreItem storeItem, ShoppingCart shoppingCart) {
        List<StoreItem> items = shoppingCart.getItems();
        items.remove(storeItem);
        shoppingCart.setItems(items);
        shoppingCartRepository.save(shoppingCart);

    }
}
