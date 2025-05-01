package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<Role> getRoles();

    void saveUser(User user, List<Long> roleIds);

    void deleteUser(User user);

    void updateUser(Long id, User updatedUser, List<Long> roleIds);
}
