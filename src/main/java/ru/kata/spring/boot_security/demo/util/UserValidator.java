package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserAuthService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Component
public class UserValidator implements Validator {
    private final UserAuthService userAuthService;

    @Autowired
    public UserValidator(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userAuthService.findOptionalByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "user.exists", "Username is already in use");
        } else if (userAuthService.findOptionalByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "user.exists", "Email is already in use");
        }
    }
}
