package com.SVO.TechTome.web;

import com.SVO.TechTome.models.WishlistItem;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.WishlistService;
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
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ModelAndView wishlist(@AuthenticationPrincipal AuthMetaData authMetaData) {
        List<WishlistItem> items = wishlistService.getWishlist(authMetaData.getId());

        ModelAndView mav = new ModelAndView("wishlist");
        mav.addObject("items", items);
        return mav;
    }

    @PostMapping("/toggle")
    public String toggle(@AuthenticationPrincipal AuthMetaData authMetaData,
                         @RequestParam UUID itemId,
                         @RequestParam(required = false) String redirectUrl) {
        wishlistService.toggle(authMetaData.getId(), itemId);
        if (redirectUrl != null && !redirectUrl.isBlank()) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/wishlist";
    }
}
