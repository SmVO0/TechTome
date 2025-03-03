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

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    private User owner;

    //@OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    //private List<StoreItem> items;


}
