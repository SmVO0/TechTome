package com.SVO.TechTome.services;

import com.SVO.TechTome.models.ShoppingCart;
import com.SVO.TechTome.models.ShoppingCartItem;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.repositories.ShoppingCartRepository;
import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.repositories.UserRepository;
import com.SVO.TechTome.web.exception.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final static int MAX_QUANTITY = 99;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }


    private void addItemToCart(ShoppingCartItem shoppingCartItem, ShoppingCart shoppingCart) {
        List<ShoppingCartItem> items = shoppingCart.getItems();
        items.add(shoppingCartItem);
        shoppingCart.setItems(items);
        shoppingCartRepository.save(shoppingCart);
    }

    private void increaseItemQuantity(StoreItem storeItem, ShoppingCart shoppingCart) {
        List<ShoppingCartItem> items = shoppingCart.getItems();
        for (ShoppingCartItem item : items) {
            if (item.getStoreItem().getId().equals(storeItem.getId())) {
                if(item.getQuantity() >= MAX_QUANTITY) {
                    throw new DomainException(("Cannot add more than %d of the same item to the cart per order. " +
                            "Please call customer service for custom order.").formatted(MAX_QUANTITY));
                }
                item.setQuantity(item.getQuantity() + 1);
                break;
            }
        }
        shoppingCart.setItems(items);
        shoppingCartRepository.save(shoppingCart);
    }

    private void decreaseItemQuantity(StoreItem storeItem, ShoppingCart shoppingCart) {
        List<ShoppingCartItem> items = shoppingCart.getItems();
        for (ShoppingCartItem item : items) {
            if (item.getStoreItem().getId().equals(storeItem.getId())) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                } else {
                    items.remove(item);
                }
                break;
            }
        }
        shoppingCart.setItems(items);
        shoppingCartRepository.save(shoppingCart);
    }

    private void removeItemFromCart(ShoppingCartItem shoppingCartItem, ShoppingCart shoppingCart) {
        List<ShoppingCartItem> items = shoppingCart.getItems();
        items.remove(shoppingCartItem);
        shoppingCart.setItems(items);
        shoppingCartRepository.save(shoppingCart);

    }

    private void clearCart(ShoppingCart shoppingCart) {
        shoppingCart.setItems(List.of());
        shoppingCartRepository.save(shoppingCart);
    }

    public List<ShoppingCartItem> getItems(UUID ownerId) {

        return shoppingCartRepository.findById(ownerId).orElseThrow().getItems();
    }

    public ShoppingCart getShoppingCart(User user) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository
                .findById(user.getShoppingCart().getId());
        return shoppingCart.orElseThrow(() -> new DomainException("Shopping cart not found for user."));
    }
}
