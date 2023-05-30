package ru.kata.spring.web.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.web.project.model.User;
import ru.kata.spring.web.project.service.UserService;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class CurrentUserRestController {

    private final UserService userService;

    public CurrentUserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/api/auth")
    public ResponseEntity<User> getAuthUser(@CurrentSecurityContext(expression = "authentication") Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }
}