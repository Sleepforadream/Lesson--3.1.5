package ru.kata.spring.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.web.project.model.User;
import ru.kata.spring.web.project.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String viewUser(Principal principal, ModelMap model) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "fragments/user-selfinfo";
    }
}