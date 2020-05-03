package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.domain.User;
import com.barancewicz.recipewebapp.exceptions.NotMatchingPswdException;
import com.barancewicz.recipewebapp.exceptions.UserAlreadyExistException;
import com.barancewicz.recipewebapp.services.RecipeService;
import com.barancewicz.recipewebapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
public class IndexController {
    public static final String REGISTRATION_PAGE = "register";
    private final RecipeService recipeService;
    private final UserService userService;
    public IndexController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }
    @GetMapping({"","/","/index","index"})
    public String getIndexPage(Model model){

        log.debug("I'm in controller, getting index page");
        model.addAttribute("recipes",recipeService.getRecipesCommands());
        return "index";
    }
    @GetMapping("/login")
    public String getLoginView(){
        return "login";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserCommand command = new UserCommand();
        model.addAttribute("user", command);
        return REGISTRATION_PAGE;
    }
    @PostMapping("register")
    public String registerUserAccount
            (@ModelAttribute("user") @Valid UserCommand userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "/register";
        }
        try {
            User registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException uaeEx) {
           model.addAttribute("message", "An account for that username/email already exists.");
           return REGISTRATION_PAGE;
        }
        catch (NotMatchingPswdException exception){
            model.addAttribute("message", "Passwords do not match");
            return REGISTRATION_PAGE;
        }
       return "redirect:/login";
    }
}
