package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/crud")
public class CrudController {
    private final UserService userService;

    @Autowired
    public CrudController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        return "/crud/admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", userService.getRoles());
        return "/crud/new";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          @RequestParam(value="roleIds", required=false) List<Long> roleIds) {
        if (bindingResult.hasErrors()) {
            return "/crud/new";
        }
        userService.saveUser(user, roleIds);
        return "redirect:/crud/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", userService.getRoles());
        return "/crud/edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @RequestParam("id") Long id,
                             @RequestParam(value="roleIds", required=false) List<Long> roleIds,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", userService.getRoles());
            if (roleIds != null) {
                List<Role> roles = new ArrayList<>(userService.getRoles());
                user.setRoles(roles);
            }
            return "/crud/edit";
        }
        userService.updateUser(id, user, roleIds);
        return "redirect:/crud/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(userService.getUserById(id));
        return "redirect:/crud/admin";
    }
}
