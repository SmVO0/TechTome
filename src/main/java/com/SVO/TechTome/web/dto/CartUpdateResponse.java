package com.SVO.TechTome.web.dto;

import java.math.BigDecimal;

public record CartUpdateResponse(int newQuantity,
                                 BigDecimal newSubtotal,
                                 BigDecimal newCartTotal,
                                 boolean itemRemoved) {}
