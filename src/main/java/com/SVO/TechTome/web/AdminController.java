package com.SVO.TechTome.web;

import com.SVO.TechTome.models.Order;
import com.SVO.TechTome.models.StoreItem;
import com.SVO.TechTome.models.Subscription;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.models.enums.OrderStatus;
import com.SVO.TechTome.services.CategoryService;
import com.SVO.TechTome.services.OrderService;
import com.SVO.TechTome.services.StoreItemService;
import com.SVO.TechTome.services.SubscriptionService;
import com.SVO.TechTome.services.UserService;
import com.SVO.TechTome.web.dto.AdminStoreItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

import static com.SVO.TechTome.constants.Constants.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;
    private final StoreItemService storeItemService;
    private final CategoryService categoryService;
    private final SubscriptionService subscriptionService;

    public AdminController(UserService userService,
                           OrderService orderService,
                           StoreItemService storeItemService,
                           CategoryService categoryService,
                           SubscriptionService subscriptionService) {
        this.userService = userService;
        this.orderService = orderService;
        this.storeItemService = storeItemService;
        this.categoryService = categoryService;
        this.subscriptionService = subscriptionService;
    }

    // ── Users ─────────────────────────────────────────────────────────────────

    @GetMapping("/users")
    public ModelAndView adminUsers(@RequestParam(required = false) String q,
                                   @RequestParam(defaultValue = "0") int page) {
        Page<User> users = userService.searchUsers(q, page);
        ModelAndView mav = new ModelAndView("adminUsers");
        mav.addObject(USERS, users);
        mav.addObject("query", q);
        mav.addObject(ACTIVE_SECTION, USERS);
        return mav;
    }

    @GetMapping("/users/{id}")
    public ModelAndView adminUserDetail(@PathVariable UUID id) {
        User user = userService.getById(id);
        Subscription subscription = subscriptionService.getActiveSubscription(user);
        List<Order> recentOrders = orderService.getRecentOrdersForUser(id, 5);
        long orderCount = orderService.countOrdersForUser(id);

        ModelAndView mav = new ModelAndView("adminUserDetail");
        mav.addObject("user", user);
        mav.addObject("subscription", subscription);
        mav.addObject("recentOrders", recentOrders);
        mav.addObject("orderCount", orderCount);
        mav.addObject(ACTIVE_SECTION, USERS);
        return mav;
    }

    // ── Products ──────────────────────────────────────────────────────────────

    @GetMapping("/products")
    public ModelAndView adminProducts() {
        ModelAndView mav = new ModelAndView("adminProducts");
        mav.addObject(PRODUCTS, storeItemService.getAllItems());
        mav.addObject(ACTIVE_SECTION, PRODUCTS);
        return mav;
    }

    @GetMapping("/products/new")
    public ModelAndView newProductForm() {
        ModelAndView mav = new ModelAndView("adminProductForm");
        mav.addObject("form", new AdminStoreItemRequest());
        mav.addObject("categories", categoryService.getAllCategories());
        mav.addObject(ACTIVE_SECTION, "products");
        mav.addObject("editMode", false);
        return mav;
    }

    @PostMapping("/products/new")
    public String createProduct(@ModelAttribute AdminStoreItemRequest form) {
        StoreItem item = StoreItem.builder()
                .name(form.getName())
                .description(form.getDescription())
                .shortDescription(form.getShortDescription())
                .price(form.getPrice())
                .image(form.getImage())
                .stock(form.getStock())
                .featured(form.isFeatured())
                .category(categoryService.getAllCategories().stream()
                        .filter(c -> c.getId().equals(form.getCategoryId()))
                        .findFirst()
                        .orElseThrow())
                .build();
        storeItemService.save(item);
        return PRODUCT_VIEW;
    }

    @GetMapping("/products/{id}/edit")
    public ModelAndView editProductForm(@PathVariable UUID id) {
        StoreItem item = storeItemService.getById(id);
        AdminStoreItemRequest form = new AdminStoreItemRequest();
        form.setName(item.getName());
        form.setDescription(item.getDescription());
        form.setShortDescription(item.getShortDescription());
        form.setPrice(item.getPrice());
        form.setImage(item.getImage());
        form.setStock(item.getStock());
        form.setFeatured(item.isFeatured());
        form.setCategoryId(item.getCategory().getId());

        ModelAndView mav = new ModelAndView("adminProductForm");
        mav.addObject("form", form);
        mav.addObject("itemId", id);
        mav.addObject("categories", categoryService.getAllCategories());
        mav.addObject(ACTIVE_SECTION, "products");
        mav.addObject("editMode", true);
        return mav;
    }

    @PostMapping("/products/{id}/edit")
    public String updateProduct(@PathVariable UUID id,
                                @ModelAttribute AdminStoreItemRequest form) {
        StoreItem item = storeItemService.getById(id);
        item.setName(form.getName());
        item.setDescription(form.getDescription());
        item.setShortDescription(form.getShortDescription());
        item.setPrice(form.getPrice());
        item.setImage(form.getImage());
        item.setStock(form.getStock());
        item.setFeatured(form.isFeatured());
        item.setCategory(categoryService.getAllCategories().stream()
                .filter(c -> c.getId().equals(form.getCategoryId()))
                .findFirst()
                .orElseThrow());
        storeItemService.save(item);
        return PRODUCT_VIEW;
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable UUID id) {
        storeItemService.deleteById(id);
        return PRODUCT_VIEW;
    }

    // ── Orders ────────────────────────────────────────────────────────────────

    @GetMapping("/orders")
    public ModelAndView adminOrders(@RequestParam(required = false) OrderStatus status) {
        List<Order> orders = orderService.getAllOrders();
        if (status != null) {
            orders = orders.stream().filter(o -> o.getStatus() == status).toList();
        }
        ModelAndView mav = new ModelAndView("adminOrders");
        mav.addObject("orders", orders);
        mav.addObject("statuses", OrderStatus.values());
        mav.addObject("selectedStatus", status);
        mav.addObject(ACTIVE_SECTION, "orders");
        return mav;
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable UUID id,
                                    @RequestParam OrderStatus status) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }
}
