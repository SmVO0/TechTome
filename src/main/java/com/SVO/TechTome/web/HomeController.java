package com.SVO.TechTome.web;

import com.SVO.TechTome.models.User;
import com.SVO.TechTome.security.AuthMetaData;
import com.SVO.TechTome.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public ModelAndView getHomepage(@AuthenticationPrincipal AuthMetaData authMetaData) {
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.getById(authMetaData.getId());

        modelAndView.setViewName("home");
        modelAndView.addObject("user", user);

        List<String> images = List.of(
                "https://picsum.photos/id/237/1200/400",
                "https://picsum.photos/id/238/1200/400",
                "https://picsum.photos/id/239/1200/400"
        );
        modelAndView.addObject("images", images);
        return modelAndView;
    }
}
