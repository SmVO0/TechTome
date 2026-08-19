package com.SVO.TechTome.web;

import com.SVO.TechTome.constants.Constants;
import com.SVO.TechTome.models.Subscription;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.enums.SubscriptionType;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.OrderService;
import com.SVO.TechTome.services.SubscriptionService;
import com.SVO.TechTome.services.UserService;
import com.SVO.TechTome.services.WishlistService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final OrderService orderService;
    private final WishlistService wishlistService;

    public UserController(UserService userService, SubscriptionService subscriptionService,
                          OrderService orderService, WishlistService wishlistService) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.orderService = orderService;
        this.wishlistService = wishlistService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String users() {
        return "redirect:/admin/users";
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
        if (authMetaData == null) return new ModelAndView("redirect:/login");
        User user = userService.getById(authMetaData.getId());
        Subscription subscription = subscriptionService.getActiveSubscription(user);

        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("user", user);
        mav.addObject("subscription", subscription);
        mav.addObject("recentOrders", orderService.getRecentOrdersForUser(user.getId(), 3));
        mav.addObject("wishlistCount", wishlistService.getWishlistedItemIds(user.getId()).size());
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

    @GetMapping("/subscription/payment")
    public ModelAndView subscriptionPayment(@AuthenticationPrincipal AuthMetaData authMetaData,
                                            @RequestParam SubscriptionType tier) {
        User user = userService.getById(authMetaData.getId());
        Subscription active = subscriptionService.getActiveSubscription(user);

        if (tier.ordinal() <= active.getType().ordinal()) {
            return new ModelAndView(Constants.SUBSCRIPTION_VIEW);
        }

        BigDecimal price = tier == SubscriptionType.PREMIUM
                ? new BigDecimal("9.99") : new BigDecimal("4.99");

        ModelAndView mav = new ModelAndView("subscription_payment");
        mav.addObject("tier", tier);
        mav.addObject("price", price);
        mav.addObject("activePlan", active.getType());
        return mav;
    }

    @PostMapping("/subscription/payment/confirm")
    public String confirmSubscriptionPayment(@AuthenticationPrincipal AuthMetaData authMetaData,
                                             @RequestParam SubscriptionType tier,
                                             RedirectAttributes redirectAttributes) {
        User user = userService.getById(authMetaData.getId());
        subscriptionService.upgrade(user, tier);
        String name = tier.name().charAt(0) + tier.name().substring(1).toLowerCase();
        redirectAttributes.addFlashAttribute("upgradeSuccess",
                "Upgraded to " + name + " — enjoy your benefits!");
        return Constants.SUBSCRIPTION_VIEW;
    }

    @GetMapping("/subscription/downgrade")
    public ModelAndView subscriptionDowngrade(@AuthenticationPrincipal AuthMetaData authMetaData,
                                              @RequestParam SubscriptionType tier) {
        User user = userService.getById(authMetaData.getId());
        Subscription active = subscriptionService.getActiveSubscription(user);

        if (tier.ordinal() >= active.getType().ordinal()) {
            return new ModelAndView(Constants.SUBSCRIPTION_VIEW);
        }

        ModelAndView mav = new ModelAndView("subscription_downgrade");
        mav.addObject("tier", tier);
        mav.addObject("active", active);
        return mav;
    }

    @PostMapping("/subscription/downgrade/confirm")
    public String confirmSubscriptionDowngrade(@AuthenticationPrincipal AuthMetaData authMetaData,
                                               @RequestParam SubscriptionType tier,
                                               RedirectAttributes redirectAttributes) {
        User user = userService.getById(authMetaData.getId());
        subscriptionService.downgrade(user, tier);
        String name = tier.name().charAt(0) + tier.name().substring(1).toLowerCase();
        redirectAttributes.addFlashAttribute("upgradeSuccess",
                "Downgraded to " + name + ".");
        return Constants.SUBSCRIPTION_VIEW;
    }
}
