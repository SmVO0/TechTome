package com.SVO.TechTome.web;

import com.SVO.TechTome.models.ShoppingCartItem;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.ShoppingCartService;
import com.SVO.TechTome.services.UserService;
import com.SVO.TechTome.web.dto.CartUpdateResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
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

    @PostMapping(value = "/add/ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CartUpdateResponse addItemAjax(@AuthenticationPrincipal AuthMetaData authMetaData,
                                          @RequestParam UUID itemId) {
        User user = userService.getById(authMetaData.getId());
        UUID cartId = user.getShoppingCart().getId();
        shoppingCartService.addItem(cartId, itemId);
        return buildCartUpdateResponse(cartId, itemId, false);
    }

    @PostMapping(value = "/remove/ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CartUpdateResponse removeItemAjax(@AuthenticationPrincipal AuthMetaData authMetaData,
                                             @RequestParam UUID itemId) {
        User user = userService.getById(authMetaData.getId());
        UUID cartId = user.getShoppingCart().getId();
        shoppingCartService.removeItem(cartId, itemId);

        List<ShoppingCartItem> items = shoppingCartService.getItems(cartId);
        boolean removed = items.stream().noneMatch(ci -> ci.getStoreItem().getId().equals(itemId));
        return buildCartUpdateResponse(cartId, itemId, removed);
    }

    private CartUpdateResponse buildCartUpdateResponse(UUID cartId, UUID itemId, boolean removed) {
        List<ShoppingCartItem> items = shoppingCartService.getItems(cartId);
        BigDecimal cartTotal = shoppingCartService.getCartTotal(cartId);

        if (removed) {
            return new CartUpdateResponse(0, BigDecimal.ZERO, cartTotal, true);
        }

        ShoppingCartItem updated = items.stream()
                .filter(ci -> ci.getStoreItem().getId().equals(itemId))
                .findFirst().orElse(null);

        if (updated == null) {
            return new CartUpdateResponse(0, BigDecimal.ZERO, cartTotal, true);
        }

        BigDecimal subtotal = updated.getUnitPrice().multiply(BigDecimal.valueOf(updated.getQuantity()));
        return new CartUpdateResponse(updated.getQuantity(), subtotal, cartTotal, false);
    }
}
