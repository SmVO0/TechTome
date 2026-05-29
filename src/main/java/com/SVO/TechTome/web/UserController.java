package com.SVO.TechTome.web;

import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.models.User;
import com.SVO.TechTome.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView users() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminUsers");

        return modelAndView;
    }

    @GetMapping("users/edit")
    @PreAuthorize("hasRole('ADMIN') || hasRole('EDITOR')")
    public ModelAndView editUsers() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");

        return modelAndView;
    }

    @GetMapping("/users/profile")
    public ModelAndView user(@AuthenticationPrincipal AuthMetaData authMetaData) {

        User user = userService.getById(authMetaData.getId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
