package ru.kata.spring.web.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @CrossOrigin(origins = "*")
    @GetMapping("/login")
    public String loginPage() {
        return "/auth/login";
    }
}
