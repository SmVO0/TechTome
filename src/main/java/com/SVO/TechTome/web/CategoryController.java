package com.SVO.TechTome.web;

import com.SVO.TechTome.models.Category;
import com.SVO.TechTome.services.CategoryService;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.StoreItemService;
import com.SVO.TechTome.services.UserService;
import com.SVO.TechTome.services.WishlistService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
@RequestMapping("/category")
public class CategoryController {


    private final CategoryService categoryService;
    private final UserService userService;
    private final StoreItemService storeItemService;
    private final WishlistService wishlistService;

    public CategoryController(CategoryService categoryService, UserService userService,
                               StoreItemService storeItemService, WishlistService wishlistService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.storeItemService = storeItemService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("/{name}")
    public ModelAndView getCategoryPage(@PathVariable String name,
                                        @RequestParam(defaultValue = "0") int page,
                                        @AuthenticationPrincipal AuthMetaData authMetaData) {
        Category category = categoryService.getCategory(name);
        Page<com.SVO.TechTome.models.StoreItem> items = storeItemService.getStoreItemsByCategory(category, page);

        ModelAndView mav = new ModelAndView("category");
        mav.addObject("items", items);
        mav.addObject("category", category);
        mav.addObject("currentPage", page);
        if (authMetaData != null) {
            mav.addObject("user", userService.getById(authMetaData.getId()));
            mav.addObject("wishlistedIds", wishlistService.getWishlistedItemIds(authMetaData.getId()));
        } else {
            mav.addObject("wishlistedIds", Collections.emptySet());
        }
        return mav;
    }
}
