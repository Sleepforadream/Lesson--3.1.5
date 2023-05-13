package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService userService,RoleService roleService) {
        this.userService = userService;
        this.roleService  = roleService;
    }

    @GetMapping("/users")
    public String viewAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/all_users";
    }

    @GetMapping("user/{id}")
    public String viewUser(@PathVariable Long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/profile";
    }

    @GetMapping("users/create")
    public String toCreateUser(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "users/create";
    }

    @PostMapping("users/create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "roles") String[] roles) {
        user.setRoles(roleService.parseArrayToListRoles(roles));
        userService.addUser(user);
        return "users/success_create";
    }

    @GetMapping("/user/{id}/update")
    public String toUpdateUser(@PathVariable Long id, ModelMap model) {
        model.addAttribute("updatableUser", userService.getUserById(id));
        return "users/update";
    }

    @PostMapping("/user/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(id, user);
        return "users/success_update";
    }

    @GetMapping("/user/{id}/delete")
    public String toDeleteUser(@PathVariable Long id, ModelMap model) {
        model.addAttribute("deletableUser", userService.getUserById(id));
        return "users/delete";
    }

    @PostMapping("/user/{id}/delete")
    public String deleteUser(@ModelAttribute("user") User user, @PathVariable("id") Long id, ModelMap model) {
        userService.deleteUser(id);
        model.addAttribute("deletedUser", user);
        return "users/success_delete";
    }
}
