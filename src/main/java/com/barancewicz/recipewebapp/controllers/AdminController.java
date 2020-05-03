package com.barancewicz.recipewebapp.controllers;

import com.barancewicz.recipewebapp.commands.CategoryCommand;
import com.barancewicz.recipewebapp.commands.UnitOfMeasureCommand;
import com.barancewicz.recipewebapp.commands.UserCommand;
import com.barancewicz.recipewebapp.services.CategoryService;
import com.barancewicz.recipewebapp.services.UnitOfMeasureService;
import com.barancewicz.recipewebapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class AdminController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final UnitOfMeasureService unitOfMeasureService;

    public AdminController(CategoryService categoryService, UserService userService, UnitOfMeasureService unitOfMeasureService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.unitOfMeasureService = unitOfMeasureService;
    }
    //===================================================================
    //====================         LIST         =========================
    //===================================================================
    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.listAllUserCommand());
        return "admin/userList";
    }
    @GetMapping("/admin/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "admin/categoryList";
    }
    @GetMapping("/admin/uoms")
    public String getUOMs(Model model) {
        model.addAttribute("uoms", unitOfMeasureService.listAllUoms());
        return "admin/uomList";
    }
    //===================================================================
    //====================         VIEW         =========================
    //===================================================================
    @GetMapping("/admin/categories/{categoryId}/show")
    public String viewCategory(@PathVariable String categoryId, Model model){
        model.addAttribute("category", categoryService.findCategoryById(Long.valueOf(categoryId)));
        return "/admin/categoryView";
    }
    @GetMapping("/admin/uoms/{uomId}/show")
    public String viewUOM(@PathVariable String uomId, Model model){
        model.addAttribute("uom", unitOfMeasureService.findUomById(Long.valueOf(uomId)));
        return "/admin/uomView";
    }
    //===================================================================
    //====================         ADD          =========================
    //===================================================================
    @GetMapping("/admin/categories/new")
    public String newCategory(Model model){
        model.addAttribute("category", new CategoryCommand());
        return "admin/categoryForm";
    }
    @GetMapping("/admin/uoms/new")
    public String newUOM(Model model){
        model.addAttribute("uom", new UnitOfMeasureCommand());
        return "admin/uomForm";
    }
    //===================================================================
    //====================         UPDATE       =========================
    //===================================================================
    @GetMapping("/admin/uoms/{uomId}/update")
    public String updateUOM(@PathVariable String uomId, Model model){
        model.addAttribute("uom", unitOfMeasureService.findUomById(Long.valueOf(uomId)));
        return "admin/uomForm";
    }
    @GetMapping("/admin/categories/{categoryId}/update")
    public String updateCategory(@PathVariable String categoryId, Model model){
        model.addAttribute("category", categoryService.findCategoryById(Long.valueOf(categoryId)));
        return "admin/categoryForm";
    }
    //===================================================================
    //====================         DELETE       =========================
    //===================================================================
    @GetMapping("/admin/categories/{categoryId}/delete")
    public String deleteCategory(@PathVariable String categoryId){
        log.debug("Deleting category, id: " + categoryId);
        categoryService.deleteById(Long.valueOf(categoryId));
        return "redirect:/admin/categories";
    }
    @GetMapping("/admin/users/{userId}/delete")
    public String deleteUser(@PathVariable String userId){
        log.debug("Deleting user, id: " + userId);
        userService.deleteById(Long.valueOf(userId));
        return "redirect:/admin/users";
    }
    //===================================================================
    @PostMapping("admin/category")
    public String saveOrUpdateCategory(@Valid @ModelAttribute("category") CategoryCommand command, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "/admin/categoryForm";
        }
        CategoryCommand saved = categoryService.saveCategory(command);
        return "redirect:/admin/categories";
    }
    @PostMapping("admin/uom")
    public String saveOrUpdateUOM(@Valid @ModelAttribute("uom") UnitOfMeasureCommand command, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return "/admin/uomForm";
        }
        UnitOfMeasureCommand saved = unitOfMeasureService.saveUOM(command);
        return "redirect:/admin/uoms";
    }
}
