package com.SVO.TechTome.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_item_id")
    private StoreItem storeItem;

    @Column(nullable = false)
    private int quantity;
}
