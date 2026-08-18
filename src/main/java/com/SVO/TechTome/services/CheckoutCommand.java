package com.SVO.TechTome.services;

public record CheckoutCommand(
        String recipientName,
        String recipientPhone,
        String deliveryStreet,
        String deliveryNum,
        String deliveryOther,
        String deliveryCity,
        String deliveryPostCode,
        String deliveryOfficeCode
) {}
