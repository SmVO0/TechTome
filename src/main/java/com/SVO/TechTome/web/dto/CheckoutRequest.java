package com.SVO.TechTome.web.dto;

public record CheckoutRequest(String recipientName,
                              String recipientPhone,
                              String deliveryAddress,
                              String deliveryCity) {}
