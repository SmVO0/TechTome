package com.SVO.TechTome.web;

import com.SVO.TechTome.models.ShoppingCartItem;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.ShoppingCartService;
import com.SVO.TechTome.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ModelAndView shoppingCart(@AuthenticationPrincipal AuthMetaData authMetaData) {
        User user = userService.getById(authMetaData.getId());
        List<ShoppingCartItem> items = shoppingCartService.getItems(user.getShoppingCart().getId());

        ModelAndView mav = new ModelAndView("shopping_cart");
        mav.addObject("user", user);
        mav.addObject("items", items);
        mav.addObject("totalPrice", user.getShoppingCart().getTotalPrice());
        return mav;
    }

    @PostMapping("/add")
    public String addItem(@AuthenticationPrincipal AuthMetaData authMetaData,
                          @RequestParam UUID itemId) {
        User user = userService.getById(authMetaData.getId());
        shoppingCartService.addItem(user.getShoppingCart().getId(), itemId);
        return "redirect:/shopping";
    }

    @PostMapping("/remove")
    public String removeItem(@AuthenticationPrincipal AuthMetaData authMetaData,
                             @RequestParam UUID itemId) {
        User user = userService.getById(authMetaData.getId());
        shoppingCartService.removeItem(user.getShoppingCart().getId(), itemId);
        return "redirect:/shopping";
    }
}
