package com.example.spring_boot_tutorial.controller;

import com.example.spring_boot_tutorial.model.User;
import com.example.spring_boot_tutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user) { // Key value from register.html input fields are passed into User model.
        userService.save(user);
        return "redirect:/";
    }
}
