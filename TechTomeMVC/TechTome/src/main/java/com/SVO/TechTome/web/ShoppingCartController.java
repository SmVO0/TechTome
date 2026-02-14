package com.SVO.TechTome.web;

import com.SVO.TechTome.models.ShoppingCart;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.ShoppingCartService;
import com.SVO.TechTome.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ModelAndView getShoppingCart(@AuthenticationPrincipal AuthMetaData authMetaData) {

        User user = userService.getById(authMetaData.getId());
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("shopping-cart");
        modelAndView.addObject("cartItems", shoppingCart.getItems());
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
