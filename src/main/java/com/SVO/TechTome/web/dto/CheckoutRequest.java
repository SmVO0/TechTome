package com.SVO.TechTome.web.dto;

public record CheckoutRequest(String recipientName,
                              String recipientPhone,
                              String deliveryStreet,
                              String deliveryNum,
                              String deliveryOther,
                              String deliveryCity,
                              String deliveryPostCode,
                              String deliveryOfficeCode) {}
