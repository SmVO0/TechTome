package com.SVO.TechTome.services;

import com.SVO.TechTome.models.Order;
import com.SVO.TechTome.models.OrderItem;
import com.SVO.TechTome.models.ShoppingCart;
import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.enums.OrderStatus;
import com.SVO.TechTome.models.enums.PaymentStatus;
import com.SVO.TechTome.repositories.OrderRepository;
import com.SVO.TechTome.repositories.StoreItemRepository;
import com.SVO.TechTome.web.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.SVO.TechTome.constants.ExceptionMessages.CART_IS_EMPTY;
import static com.SVO.TechTome.constants.ExceptionMessages.INSUFFICIENT_STOCK;
import static com.SVO.TechTome.constants.ExceptionMessages.INVALID_DELIVERY_CITY;
import static com.SVO.TechTome.constants.ExceptionMessages.ORDER_NOT_FOUND;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final StoreItemRepository storeItemRepository;
    private final SubscriptionService subscriptionService;
    private final DeliveryService deliveryService;
    private final EcontCityService econtCityService;

    public OrderService(OrderRepository orderRepository,
                        UserService userService,
                        ShoppingCartService shoppingCartService,
                        StoreItemRepository storeItemRepository,
                        SubscriptionService subscriptionService,
                        DeliveryService deliveryService,
                        EcontCityService econtCityService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.storeItemRepository = storeItemRepository;
        this.subscriptionService = subscriptionService;
        this.deliveryService = deliveryService;
        this.econtCityService = econtCityService;
    }

    private static final BigDecimal DEFAULT_DELIVERY_COST = new BigDecimal("3.99");

    @Transactional
    public Order checkout(UUID userId, CheckoutCommand cmd) {
        User user = userService.getById(userId);
        ShoppingCart cart = user.getShoppingCart();

        if (cart.getItems().isEmpty()) {
            throw new DomainException(CART_IS_EMPTY);
        }

        boolean isOffice = cmd.deliveryOfficeCode() != null && !cmd.deliveryOfficeCode().isBlank();

        if (!isOffice && !econtCityService.isValidCity(cmd.deliveryCity(), cmd.deliveryPostCode())) {
            throw new DomainException(INVALID_DELIVERY_CITY.formatted(cmd.deliveryCity(), cmd.deliveryPostCode()));
        }

        cart.getItems().forEach(ci -> {
            StoreItem si = ci.getStoreItem();
            if (si.getStock() > 0 && ci.getQuantity() > si.getStock()) {
                throw new DomainException(INSUFFICIENT_STOCK.formatted(si.getName(), si.getStock()));
            }
        });

        BigDecimal rawTotal = cart.getTotalPrice();
        BigDecimal discountMultiplier = subscriptionService.getDiscountMultiplier(user);
        BigDecimal discountedTotal = rawTotal.multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);

        BigDecimal calculatedCost = isOffice
                ? DEFAULT_DELIVERY_COST
                : deliveryService.calculateDeliveryCost(
                        cmd.deliveryCity(), cmd.deliveryPostCode(),
                        cmd.deliveryStreet(), cmd.deliveryNum(), cmd.deliveryOther());
        BigDecimal deliveryCost = subscriptionService.isShippingFree(user, discountedTotal) ? BigDecimal.ZERO : calculatedCost;

        Order order = Order.builder()
                .buyer(user)
                .createdOn(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .totalPrice(discountedTotal)
                .deliveryCost(deliveryCost)
                .paymentStatus(PaymentStatus.MOCK_PAID)
                .recipientName(cmd.recipientName())
                .recipientPhone(cmd.recipientPhone())
                .deliveryStreet(cmd.deliveryStreet())
                .deliveryNum(cmd.deliveryNum())
                .deliveryOther(cmd.deliveryOther())
                .deliveryCity(cmd.deliveryCity())
                .deliveryPostCode(cmd.deliveryPostCode())
                .deliveryOfficeCode(isOffice ? cmd.deliveryOfficeCode() : null)
                .build();
        Order saved = orderRepository.save(order);

        cart.getItems().forEach(ci -> {
            StoreItem si = ci.getStoreItem();
            if (si.getStock() > 0) {
                si.setStock(si.getStock() - ci.getQuantity());
                storeItemRepository.save(si);
            }
            saved.getItems().add(OrderItem.builder()
                    .order(saved)
                    .storeItem(si)
                    .quantity(ci.getQuantity())
                    .unitPrice(ci.getUnitPrice())
                    .build());
        });

        String trackingNumber = deliveryService.createShipment(saved);
        saved.setTrackingNumber(trackingNumber);

        orderRepository.save(saved);

        shoppingCartService.clearCart(cart.getId());

        log.info("Order [{}] created for user [{}] — total: {}, delivery: {}, tracking: {}",
                saved.getId(), user.getEmail(), discountedTotal, deliveryCost, trackingNumber);
        return saved;
    }

    public List<Order> getOrdersForUser(UUID userId) {
        User user = userService.getById(userId);
        return orderRepository.findByBuyerOrderByCreatedOnDesc(user);
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DomainException(ORDER_NOT_FOUND.formatted(orderId)));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedOnDesc();
    }

    public List<Order> getRecentOrdersForUser(UUID userId, int limit) {
        User user = userService.getById(userId);
        return orderRepository.findByBuyerOrderByCreatedOnDesc(user, PageRequest.of(0, limit)).getContent();
    }

    public long countOrdersForUser(UUID userId) {
        User user = userService.getById(userId);
        return orderRepository.countByBuyer(user);
    }

    public void updateStatus(UUID orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
        log.info("Order [{}] status updated to [{}].", orderId, status);
    }
}
