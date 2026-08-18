package com.SVO.TechTome.web;

import com.SVO.TechTome.models.Order;
import com.SVO.TechTome.models.ShoppingCartItem;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.CheckoutCommand;
import com.SVO.TechTome.services.DeliveryService;
import com.SVO.TechTome.services.OrderService;
import com.SVO.TechTome.services.ShoppingCartService;
import com.SVO.TechTome.services.UserService;
import com.SVO.TechTome.web.dto.CheckoutRequest;
import com.SVO.TechTome.web.exception.DomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final DeliveryService deliveryService;

    public OrderController(OrderService orderService,
                           UserService userService,
                           ShoppingCartService shoppingCartService,
                           DeliveryService deliveryService) {
        this.orderService = orderService;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.deliveryService = deliveryService;
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
                      @ModelAttribute CheckoutRequest form,
                      RedirectAttributes redirectAttributes) {
        try {
            CheckoutCommand cmd = new CheckoutCommand(
                    form.recipientName(), form.recipientPhone(),
                    form.deliveryStreet(), form.deliveryNum(), form.deliveryOther(),
                    form.deliveryCity(), form.deliveryPostCode());
            Order order = orderService.checkout(authMetaData.getId(), cmd);
            return "redirect:/orders/" + order.getId();
        } catch (DomainException e) {
            redirectAttributes.addFlashAttribute("checkoutError", e.getMessage());
            redirectAttributes.addFlashAttribute("checkoutForm", form);
            return "redirect:/checkout/payment";
        }
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

    @GetMapping("/orders/{id}/tracking")
    @ResponseBody
    public ResponseEntity<DeliveryService.TrackingStatus> trackingStatus(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);
        if (order.getTrackingNumber() == null) {
            return ResponseEntity.noContent().build();
        }
        DeliveryService.TrackingStatus status = deliveryService.getTrackingStatus(order.getTrackingNumber());
        return status != null ? ResponseEntity.ok(status) : ResponseEntity.noContent().build();
    }
}
