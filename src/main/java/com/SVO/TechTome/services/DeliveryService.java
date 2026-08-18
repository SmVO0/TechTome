package com.SVO.TechTome.services;

import com.SVO.TechTome.models.Order;

import java.math.BigDecimal;

public interface DeliveryService {

    String createShipment(Order order);

    BigDecimal calculateDeliveryCost(String cityName, String postCode, String street, String num, String other);

    TrackingStatus getTrackingStatus(String trackingNumber);

    record TrackingStatus(String statusDescription, String lastEventDate) {}
}
