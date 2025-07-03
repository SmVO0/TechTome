package com.SVO.TechTome.web;

import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.user.model.User;
import com.SVO.TechTome.user.repository.UserRepository;
import com.SVO.TechTome.user.service.UserService;
import com.SVO.TechTome.web.dto.LoginRequest;
import com.SVO.TechTome.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class IndexController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public IndexController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
        modelAndView.setViewName("register");
        RegisterRequest registerRequest = new RegisterRequest();
        modelAndView.addObject("registerRequest", registerRequest);

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("register");
        }

        userService.register(registerRequest);

        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(@AuthenticationPrincipal AuthMetaData authMetaData) {

        List<String> imageUrls = List.of(
                "static/images/slide1.jpg",
                "static/images/slide2.jpg",
                "static/images/slide3.jpg"
        );

        User user = userService.getById(authMetaData.getId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
