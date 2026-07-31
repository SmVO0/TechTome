package com.SVO.TechTome.services;

import com.SVO.TechTome.models.Order;
import com.SVO.TechTome.models.OrderItem;
import com.SVO.TechTome.models.ShoppingCart;
import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.enums.OrderStatus;
import com.SVO.TechTome.repositories.OrderRepository;
import com.SVO.TechTome.repositories.StoreItemRepository;
import com.SVO.TechTome.web.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.SVO.TechTome.constants.ExceptionMessages.CART_IS_EMPTY;
import static com.SVO.TechTome.constants.ExceptionMessages.INSUFFICIENT_STOCK;
import static com.SVO.TechTome.constants.ExceptionMessages.ORDER_NOT_FOUND;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final StoreItemRepository storeItemRepository;

    public OrderService(OrderRepository orderRepository,
                        UserService userService,
                        ShoppingCartService shoppingCartService,
                        StoreItemRepository storeItemRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.storeItemRepository = storeItemRepository;
    }

    @Transactional
    public Order checkout(UUID userId) {
        User user = userService.getById(userId);
        ShoppingCart cart = user.getShoppingCart();

        if (cart.getItems().isEmpty()) {
            throw new DomainException(CART_IS_EMPTY);
        }

        cart.getItems().forEach(ci -> {
            StoreItem si = ci.getStoreItem();
            if (si.getStock() > 0 && ci.getQuantity() > si.getStock()) {
                throw new DomainException(INSUFFICIENT_STOCK.formatted(si.getName(), si.getStock()));
            }
        });

        Order order = Order.builder()
                .buyer(user)
                .createdOn(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .totalPrice(cart.getTotalPrice())
                .build();
        Order saved = orderRepository.save(order);

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(ci -> {
                    StoreItem si = ci.getStoreItem();
                    if (si.getStock() > 0) {
                        si.setStock(si.getStock() - ci.getQuantity());
                        storeItemRepository.save(si);
                    }
                    return OrderItem.builder()
                            .order(saved)
                            .storeItem(si)
                            .quantity(ci.getQuantity())
                            .unitPrice(ci.getUnitPrice())
                            .build();
                })
                .toList();

        saved.setItems(orderItems);
        orderRepository.save(saved);

        shoppingCartService.clearCart(cart.getId());

        log.info("Order [{}] created for user [{}]", saved.getId(), user.getEmail());
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
}
