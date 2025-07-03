package com.SVO.TechTome.web.dto;

import com.SVO.TechTome.category.model.Category;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreItemRequest {

    @Size(min = 6, message = "Name must be at least 6 characters")
    private String name;

    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;


    private BigDecimal price;

    @Size
    private String imageURL;

    @Size
    private Category category;
}
