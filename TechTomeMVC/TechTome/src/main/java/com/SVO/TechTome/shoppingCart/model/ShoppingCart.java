package com.SVO.TechTome.shoppingCart.model;

import com.SVO.TechTome.storeItem.model.StoreItem;
import com.SVO.TechTome.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCart {

    @Column(nullable = false, unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "shoppingCart")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_store_items",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "store_item_id")
    )
    private List<StoreItem> items;


}
