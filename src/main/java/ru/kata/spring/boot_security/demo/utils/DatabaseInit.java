package ru.kata.spring.boot_security.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInit {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInit(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        if (roleRepository.count() == 0) {
            Role userRole = new Role("USER");
            Role adminRole = new Role("ADMIN");
            roleRepository.saveAll(List.of(userRole, adminRole));
        }

        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN");
            Role userRole = roleRepository.findByName("USER");

            User admin = new User(
                    "admin",
                    "admin",
                    (byte) 30,
                    "admin@gmail.com",
                    passwordEncoder.encode("admin")
            );
            if (admin.getRoles() == null) {
                admin.setRoles(new ArrayList<>());
            }
            admin.getRoles().add(adminRole);
            admin.getRoles().add(userRole);
            userRepository.save(admin);

            User user = new User(
                    "user",
                    "user", (byte) 18,
                    "user@gmail.com",
                    passwordEncoder.encode("user")
            );
            if (user.getRoles() == null) {
                user.setRoles(new ArrayList<>());
            }
            user.getRoles().add(userRole);
            userRepository.save(user);
        }
    }
}
