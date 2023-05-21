package ru.kata.spring.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.web.project.model.User;
import ru.kata.spring.web.project.service.RoleService;
import ru.kata.spring.web.project.service.UserService;

@Component
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        model.addAttribute("allRoles", roleService.getAllRoles());

//        model.addAttribute("showUserProfile", model.containsAttribute("user") &&
//                        (model.getAttribute("user")) == null);
//
//        model.addAttribute("showNewUserForm", model.containsAttribute("user") &&
//                        (model.getAttribute("user")) != null);
//
//        if (!model.containsAttribute("user")) {
//            model.addAttribute("user", new User());
//        }

        return "users/admin";
    }

    @GetMapping("/user/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/profile";
    }

    @GetMapping("/users/create")
    public String toCreateUser() {
        return "users/create";
    }

    @PostMapping("/fragments/user-addform")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        userService.addUser(user);
        model.addAttribute("createdUser", user);
        return "users/success_create";
    }

    @GetMapping("/user/{id}/update")
    public String toUpdateUser(@PathVariable Long id, Model model) {
        model.addAttribute("updatableUser", userService.getUserById(id));
        return "users/update";
    }

    @PostMapping("/user/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, Model model) {
        userService.updateUser(id, user);
        model.addAttribute("updatedUser", user);
        return "users/success_update";
    }

    @GetMapping("/user/{id}/delete")
    public String toDeleteUser(@PathVariable Long id, Model model) {
        model.addAttribute("deletableUser", userService.getUserById(id));
        return "users/delete";
    }

    @PostMapping("/user/{id}/delete")
    public String deleteUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("deletedUser", user);
        return "users/success_delete";
    }
}
