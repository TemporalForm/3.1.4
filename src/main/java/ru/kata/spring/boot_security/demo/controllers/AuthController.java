package ru.kata.spring.boot_security.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dtos.UserDTO;
import ru.kata.spring.boot_security.demo.model_mappers.ControllerModelMapper;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserRegisterService;
import ru.kata.spring.boot_security.demo.utils.UserValidator;
import javax.validation.Valid;
@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserRegisterService userRegisterService;
    private final UserValidator userValidator;
    private final ControllerModelMapper modelMapper;
    @Autowired
    public AuthController(UserRegisterService userRegisterService, UserValidator userValidator, ControllerModelMapper modelMapper) {
        this.userValidator = userValidator;
        this.userRegisterService = userRegisterService;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO dto,
                                      BindingResult br) {
        User user = modelMapper.convertToUser(dto);
        userValidator.validate(user, br);
        if (br.hasErrors()) {
            return ResponseEntity.badRequest().body(br.getAllErrors());
        }
        userRegisterService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
