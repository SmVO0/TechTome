package com.SVO.TechTome.services;

import com.SVO.TechTome.models.Order;

import java.math.BigDecimal;

public interface DeliveryService {

    /**
     * Creates a shipment via the delivery provider.
     * Returns the tracking number, or null if the provider is unavailable.
     */
    String createShipment(Order order);

    /**
     * Calculates the delivery cost for the given destination address.
     */
    BigDecimal calculateDeliveryCost(String cityName, String postCode, String street, String num, String other);
}
