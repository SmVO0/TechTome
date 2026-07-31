package com.SVO.TechTome.web;

import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.StoreItemService;
import com.SVO.TechTome.services.WishlistService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/item")
public class StoreItemController {

    private final StoreItemService storeItemService;
    private final WishlistService wishlistService;

    public StoreItemController(StoreItemService storeItemService, WishlistService wishlistService) {
        this.storeItemService = storeItemService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("/{id}")
    public ModelAndView getItemPage(@PathVariable UUID id,
                                    @AuthenticationPrincipal AuthMetaData authMetaData) {
        StoreItem item = storeItemService.getById(id);

        boolean wishlisted = authMetaData != null
                && wishlistService.isWishlisted(authMetaData.getId(), id);

        ModelAndView mav = new ModelAndView("item");
        mav.addObject("item", item);
        mav.addObject("wishlisted", wishlisted);
        return mav;
    }
}
