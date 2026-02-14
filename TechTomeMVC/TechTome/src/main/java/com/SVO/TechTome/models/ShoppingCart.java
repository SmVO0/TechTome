package com.SVO.TechTome.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private User owner;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column
    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartItem> items;


}
