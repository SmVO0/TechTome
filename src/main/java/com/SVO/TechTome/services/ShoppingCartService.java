package com.SVO.TechTome.services;

import com.SVO.TechTome.models.ShoppingCart;
import com.SVO.TechTome.repositories.ShoppingCartRepository;
import com.SVO.TechTome.models.StoreItem;
import org.springframework.stereotype.Service;

import java.util.List;

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
