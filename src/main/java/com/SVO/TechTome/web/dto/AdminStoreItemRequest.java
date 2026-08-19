package com.SVO.TechTome.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AdminStoreItemRequest {
    private String name;
    private String description;
    private String shortDescription;
    private BigDecimal price;
    private String image;
    private int stock;
    private boolean featured;
    private UUID categoryId;
    private BigDecimal rating;
    private int reviewCount;
}
