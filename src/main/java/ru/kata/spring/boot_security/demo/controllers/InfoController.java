package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.utils.ControllerUtil;

@Controller
public class InfoController {
    private final UserService userService;

    @Autowired
    public InfoController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addUserInfo(Model model, Authentication auth) {
        model.addAttribute("authorisedUserEmail", auth.getName());
        model.addAttribute("authorisedUserRoles", ControllerUtil.getAuthoritiesFromSecurityToken(auth));
    }

    @GetMapping("/user")
    public String userPage(Model model,
                           Authentication auth) {
        model.addAttribute("user",
                userService.getUserByUsername(auth.getName()));
        model.addAttribute("tab", "user");
        return "info/user";
    }
}
