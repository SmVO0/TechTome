package com.SVO.TechTome.web;

import com.SVO.TechTome.models.Category;
import com.SVO.TechTome.services.CategoryService;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.services.StoreItemService;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final StoreItemService storeItemService;

    public CategoryController(CategoryService categoryService, UserService userService, StoreItemService storeItemService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.storeItemService = storeItemService;
    }

    @GetMapping("/{name}")
    public ModelAndView getCategoryPage(@PathVariable String name, @AuthenticationPrincipal AuthMetaData authMetaData) {

        User user = userService.getById(authMetaData.getId());
        Category category = categoryService.getCategory(name);
        List<StoreItem> items = storeItemService.getStoreItemsByCategory(category);

         ModelAndView modelAndView = new ModelAndView();
         modelAndView.setViewName("category");
         modelAndView.addObject("items", items);
         modelAndView.addObject("user", user);

         return modelAndView;

    }
}
