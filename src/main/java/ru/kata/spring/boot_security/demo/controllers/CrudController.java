package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.utils.ControllerUtil;

import javax.validation.Valid;
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
    public String printUsers(ModelMap model, Authentication auth) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("allRoles", userService.getRoles());
        model.addAttribute("tab", "table");
        model.addAttribute("user", new User());
        model.addAttribute("showEditModal", false);
        model.addAttribute("authorisedUserEmail", auth.getName());
        model.addAttribute("authorisedUserRoles",
                ControllerUtil.getAuthoritiesFromSecurityToken(auth));
        return "crud/admin";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model, Authentication auth) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getRoles());
        model.addAttribute("tab", "new");
        model.addAttribute("showEditModal", false);          
        model.addAttribute("authorisedUserEmail", auth.getName());
        model.addAttribute("authorisedUserRoles",
                ControllerUtil.getAuthoritiesFromSecurityToken(auth));
        return "crud/admin";
    }

    @PostMapping("/admin")
    public String createUser(
            @ModelAttribute("user") @Valid User user,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            BindingResult br,
            Model model,
            Authentication auth) {
        if (br.hasErrors()) {
            if (user.getId() == null) {
                model.addAttribute("tab", "new");
            } else {
                model.addAttribute("tab", "table"); 
            }
            model.addAttribute("allRoles", userService.getRoles());
            model.addAttribute("authorisedUserEmail", auth.getName());
            model.addAttribute("authorisedUserRoles", ControllerUtil.getAuthoritiesFromSecurityToken(auth));
            model.addAttribute("allRoles", userService.getRoles());
            return "crud/admin";
        }

        if (user.getId() == null) {
            userService.saveUser(user);
        }

        else {
            userService.saveUser(user, roleIds);
        }
        return "redirect:/crud/admin";
    }



    @PostMapping("/update")
    public String updateUser(
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            @RequestParam(value = "roleIds", required = false) List<Long> roleIds,
            @RequestParam("id") Long id,
            ModelMap model,
            Authentication auth) {

        user.setId(id); 
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("user", user); 
            model.addAttribute("users", userService.getUsers());
            model.addAttribute("allRoles", userService.getRoles());
            model.addAttribute("tab", "table");
            model.addAttribute("authorisedUserEmail", auth.getName());
            model.addAttribute("authorisedUserRoles",
                    ControllerUtil.getAuthoritiesFromSecurityToken(auth));
            model.addAttribute("showEditModal", true);
            return "crud/admin";
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
