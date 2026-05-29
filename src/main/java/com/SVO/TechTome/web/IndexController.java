package com.SVO.TechTome.web;

import com.SVO.TechTome.services.UserService;
import com.SVO.TechTome.web.dto.LoginRequest;
import com.SVO.TechTome.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@Controller
public class IndexController {

    private final UserService userService;
    private static final String REGISTER_URL = "register";

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage() {

        return "index";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) String errorParam) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        LoginRequest loginRequest = new LoginRequest();
        modelAndView.addObject("loginRequest", loginRequest);

        if (errorParam != null) {
            modelAndView.addObject("error", errorParam);
        }

        return modelAndView;
    }

    @PostMapping("/login")
    public String login(@Valid LoginRequest loginRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        return "redirect:/home";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(REGISTER_URL);
        RegisterRequest registerRequest = new RegisterRequest();
        modelAndView.addObject("registerRequest", registerRequest);

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView(REGISTER_URL);
        }

        userService.register(registerRequest);

        return new ModelAndView(REGISTER_URL);
    }
}
