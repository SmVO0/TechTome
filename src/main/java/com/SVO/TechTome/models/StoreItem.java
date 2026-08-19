package com.SVO.TechTome.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false, columnDefinition = "int default 100")
    private int stock;

    private boolean featured;

    @Column
    private String shortDescription;

    @Column(columnDefinition = "DECIMAL(2,1) DEFAULT 0.0")
    private BigDecimal rating;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int reviewCount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
