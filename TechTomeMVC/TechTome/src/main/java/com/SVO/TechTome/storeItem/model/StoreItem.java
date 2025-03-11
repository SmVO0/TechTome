package com.SVO.TechTome.storeItem.model;

import com.SVO.TechTome.category.model.Category;
import com.SVO.TechTome.shoppingCart.model.ShoppingCart;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
public class StoreItem {

    @Column(nullable = false, unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, unique = true)
    private String image;

    @ManyToOne
    private Category category;

    @ManyToMany
    private List<ShoppingCart> shoppingCart;
}
