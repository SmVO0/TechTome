package com.SVO.TechTome.constants;

public final class ExceptionMessages {

    private ExceptionMessages() {}

    public static final String USER_ALREADY_EXISTS = "Username with email [%s] already exist.";
    public static final String USER_NOT_FOUND_BY_EMAIL = "User with this email not found.";
    public static final String USER_NOT_FOUND_BY_ID = "User with id [%s] not found.";
    public static final String SHOPPING_CART_NOT_FOUND = "Shopping cart not found.";
    public static final String STORE_ITEM_NOT_FOUND = "Item not found.";
    public static final String INSUFFICIENT_STOCK = "Not enough stock for [%s]. Available: %d.";
    public static final String CART_IS_EMPTY = "Cannot checkout an empty cart.";
    public static final String ORDER_NOT_FOUND = "Order with id [%s] not found.";
    public static final String INVALID_DELIVERY_CITY = "City \"%s\" with postal code \"%s\" was not found. Please select a city from the dropdown.";
    public static final String INVALID_DELIVERY_STREET = "The street was not found in the selected city. Please check the street name and try again.";
}
