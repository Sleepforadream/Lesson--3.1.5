package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Controller
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
        return "users/all_users";
    }

    @GetMapping("user/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/profile";
    }

    @GetMapping("users/create")
    public String toCreateUser() {
        return "users/create";
    }

    @PostMapping("users/create")
    public String createUser(@ModelAttribute("user") User user, Model model) {
//        User user = new User(username, password, name, surname, age);
//        user.setRoles(roleService.parseArrayToSet(user));
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
    public String updateUser(@RequestParam("username") String username,
                             @RequestParam(value = "age", required = false) Integer age,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "surname", required = false) String surname,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "roles") String[] roles,
                             @PathVariable("id") Long id,
                             Model model) {
        User user = new User(username, password, name, surname, age);
        user.setRoles(roleService.parseArrayToSet(roles));
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
