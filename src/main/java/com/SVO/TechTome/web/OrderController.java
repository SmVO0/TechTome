package com.SVO.TechTome.web;

import com.SVO.TechTome.models.Order;
import com.SVO.TechTome.models.ShoppingCartItem;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.OrderService;
import com.SVO.TechTome.services.ShoppingCartService;
import com.SVO.TechTome.services.UserService;
import com.SVO.TechTome.web.dto.CheckoutRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService,
                           UserService userService,
                           ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/checkout/payment")
    public ModelAndView paymentForm(@AuthenticationPrincipal AuthMetaData authMetaData) {
        User user = userService.getById(authMetaData.getId());
        List<ShoppingCartItem> items = shoppingCartService.getItems(user.getShoppingCart().getId());

        ModelAndView mav = new ModelAndView("payment");
        mav.addObject("user", user);
        mav.addObject("items", items);
        mav.addObject("cartTotal", user.getShoppingCart().getTotalPrice());
        return mav;
    }

    @PostMapping("/checkout/pay")
    public String pay(@AuthenticationPrincipal AuthMetaData authMetaData,
                      @ModelAttribute CheckoutRequest form) {
        Order order = orderService.checkout(authMetaData.getId(),
                form.recipientName(), form.recipientPhone(),
                form.deliveryAddress(), form.deliveryCity());
        return "redirect:/orders/" + order.getId();
    }

    @GetMapping("/orders")
    public ModelAndView orderHistory(@AuthenticationPrincipal AuthMetaData authMetaData) {
        List<Order> orders = orderService.getOrdersForUser(authMetaData.getId());

        ModelAndView mav = new ModelAndView("order_history");
        mav.addObject("orders", orders);
        return mav;
    }

    @GetMapping("/orders/{id}")
    public ModelAndView orderDetail(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);

        ModelAndView mav = new ModelAndView("order_detail");
        mav.addObject("order", order);
        return mav;
    }
}
