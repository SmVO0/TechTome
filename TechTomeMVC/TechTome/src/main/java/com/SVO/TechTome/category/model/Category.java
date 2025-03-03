package com.SVO.TechTome.category.model;

import com.SVO.TechTome.storeItem.model.StoreItem;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique=true, nullable=false)
    private String name;

    @OneToMany
    private List<StoreItem> storeItems;
}
