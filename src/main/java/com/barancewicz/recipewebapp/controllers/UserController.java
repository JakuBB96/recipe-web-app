package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{username}/profile")
    public String getProfile(@PathVariable String userName, Model model){
        model.addAttribute("user", userService.findByUsername(userName));
        return "user/dashboard";
    }
}
