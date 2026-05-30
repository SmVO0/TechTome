package com.SVO.TechTome.repositories;

import com.SVO.TechTome.models.ShoppingCart;
import com.SVO.TechTome.models.ShoppingCartItem;
import com.SVO.TechTome.models.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, UUID> {

    Optional<ShoppingCartItem> findByCartAndStoreItem(ShoppingCart cart, StoreItem storeItem);

    List<ShoppingCartItem> findByCart(ShoppingCart cart);
}
