package com.SVO.TechTome.web;

import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.StoreItemService;
import com.SVO.TechTome.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final StoreItemService storeItemService;

    public HomeController(UserService userService, StoreItemService storeItemService) {
        this.userService = userService;
        this.storeItemService = storeItemService;
    }

    @GetMapping
    public ModelAndView getHomepage(@AuthenticationPrincipal AuthMetaData authMetaData) {
        ModelAndView mav = new ModelAndView("home");

        if (authMetaData != null) {
            mav.addObject("user", userService.getById(authMetaData.getId()));
        }

        mav.addObject("featuredItems", storeItemService.getFeaturedItems());
        return mav;
    }
}
