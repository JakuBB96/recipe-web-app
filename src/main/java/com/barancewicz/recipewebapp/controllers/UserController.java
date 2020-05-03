package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.exceptions.NotMatchingPswdException;
import com.barancewicz.recipewebapp.exceptions.UserAlreadyExistException;
import com.barancewicz.recipewebapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userName}/profile")
    public String getProfile(@PathVariable String userName, Model model){
        model.addAttribute("user", userService.findCommandByUsername(userName));
        return "user/dashboard";
    }

    @PostMapping("/users/edit")
    public String changePassword
            (@ModelAttribute("user") @Valid UserCommand userDto, BindingResult bindingResult, Model model) {
            if (bindingResult.hasFieldErrors("password")||bindingResult.hasFieldErrors("matchingPassword")){
            BindingResult newBindingResult = new BeanPropertyBindingResult(userDto, "user");
            bindingResult.getAllErrors().forEach(objectError -> newBindingResult.addError(objectError));
            if (newBindingResult.hasErrors()){
                newBindingResult.getAllErrors().forEach(objectError -> {
                    log.debug(objectError.toString());
                });
                return "user/dashboard";
            }
        }
        try {
            UserCommand updated = userService.changePswd(userDto);
        }
        catch (NotMatchingPswdException exception){
            model.addAttribute("message", "Passwords do not match");
            return "user/dashboard";
        }

        return "redirect:/logout";
    }
}
