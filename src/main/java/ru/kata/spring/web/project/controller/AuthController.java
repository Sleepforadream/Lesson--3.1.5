package ru.kata.spring.web.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }
}
