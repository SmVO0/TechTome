package com.SVO.TechTome.web;

import com.SVO.TechTome.models.Subscription;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.enums.SubscriptionType;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.SubscriptionService;
import com.SVO.TechTome.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;

    public UserController(UserService userService, SubscriptionService subscriptionService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView users() {
        List<User> allUsers = userService.getAllUsers();

        ModelAndView mav = new ModelAndView("adminUsers");
        mav.addObject("users", allUsers);
        return mav;
    }

    @GetMapping("/edit")
    @PreAuthorize("hasRole('ADMIN') || hasRole('EDITOR')")
    public ModelAndView editUsers(@AuthenticationPrincipal AuthMetaData authMetaData) {
        User user = userService.getById(authMetaData.getId());

        ModelAndView mav = new ModelAndView("profile_info");
        mav.addObject("user", user);
        mav.addObject("editMode", true);
        return mav;
    }

    @GetMapping("/profile")
    public ModelAndView userProfile(@AuthenticationPrincipal AuthMetaData authMetaData) {
        User user = userService.getById(authMetaData.getId());

        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", user);
        return mav;
    }

    @GetMapping("/profile/info")
    public ModelAndView profileInfo(@AuthenticationPrincipal AuthMetaData authMetaData) {
        User user = userService.getById(authMetaData.getId());

        ModelAndView mav = new ModelAndView("profile_info");
        mav.addObject("user", user);
        return mav;
    }

    @GetMapping("/subscription")
    public ModelAndView subscription(@AuthenticationPrincipal AuthMetaData authMetaData) {
        User user = userService.getById(authMetaData.getId());
        Subscription active = subscriptionService.getActiveSubscription(user);

        ModelAndView mav = new ModelAndView("subscription");
        mav.addObject("user", user);
        mav.addObject("active", active);
        mav.addObject("tiers", SubscriptionType.values());
        return mav;
    }

    @PostMapping("/subscription/upgrade")
    public String upgrade(@AuthenticationPrincipal AuthMetaData authMetaData,
                          @RequestParam SubscriptionType newType) {
        User user = userService.getById(authMetaData.getId());
        subscriptionService.upgrade(user, newType);
        return "redirect:/users/subscription";
    }
}
