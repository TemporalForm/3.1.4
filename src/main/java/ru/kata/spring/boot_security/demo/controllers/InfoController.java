package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.security.Principal;
import java.util.Optional;

@Controller
public class InfoController {
    private final UserRepository userRepository;

    @Autowired
    public InfoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String welcomePage() {
        return "/info/welcome";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        User user = userRepository
                .getUserByUsernameWithRoles(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); 
        model.addAttribute("user", user);
        return "info/user";  
    }
}
