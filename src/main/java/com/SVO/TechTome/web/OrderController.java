package com.SVO.TechTome.web;

import com.SVO.TechTome.models.Order;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal AuthMetaData authMetaData) {
        Order order = orderService.checkout(authMetaData.getId());
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
