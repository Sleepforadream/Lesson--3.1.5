package com.example.PP_3_1_2_spring_project_boot.controller;

import com.example.PP_3_1_2_spring_project_boot.model.User;
import com.example.PP_3_1_2_spring_project_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String viewAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/profile";
    }
    @GetMapping("/create")
    public String toCreateUser() {
        return "users/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "users/success_create";
    }

    @GetMapping("/{id}/update")
    public String toUpdateUser(@PathVariable Long id, ModelMap model) {
        model.addAttribute("updatableUser",userService.getUserById(id));
        return "users/update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user,@PathVariable("id") Long id) {
        userService.updateUser(id,user);
        return "users/success_update";
    }

    @GetMapping("/{id}/delete")
    public String toDeleteUser(@PathVariable Long id, ModelMap model) {
        model.addAttribute("deletableUser",userService.getUserById(id));
        return "users/delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, ModelMap model) {
        userService.deleteUser(id);
        model.addAttribute("user2", user);
        return "users/success_delete";
    }
}