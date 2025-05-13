package ru.kata.spring.boot_security.demo.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dtos.UserDTO;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.securities.TokenDetails;
@RestController
@RequestMapping("/api")
public class InfoController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Autowired
    public InfoController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/user")
    public UserDTO currentUser(Authentication auth) {
        return modelMapper.map(
                userService.getUserByUsername(auth.getName()),
                UserDTO.class
        );
    }
}
