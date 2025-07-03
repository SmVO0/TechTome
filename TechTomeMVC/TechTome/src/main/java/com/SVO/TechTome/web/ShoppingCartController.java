package com.SVO.TechTome.web;

import com.SVO.TechTome.shoppingCart.service.ShoppingCartService;
import com.SVO.TechTome.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

//    @GetMapping
//    public ModelAndView shoppingCart(@AuthenticationPrincipal AuthMetaData authMetaData) {
//
//        User user = userService.getById(authMetaData.getId());
//        List<StoreItem> items = shoppingCartService.getAllByOwnerId(authMetaData.getId());
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("transactions");
//        modelAndView.addObject("transactions", transactions);
//        modelAndView.addObject("user", user);
//    }


}
