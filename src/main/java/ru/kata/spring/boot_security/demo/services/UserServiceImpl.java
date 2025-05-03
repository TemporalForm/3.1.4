package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDAO(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user, List<Long> roleIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roles = (roleIds == null)
                ? Collections.emptyList()
                : roleRepository.findAllById(roleIds);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User updatedUser, List<Long> roleIds) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        userToUpdate.setFirstname(updatedUser.getFirstname());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        List<Role> roles = (roleIds == null)
                ? Collections.emptyList()
                : roleRepository.findAllById(roleIds);
        userToUpdate.setRoles(roles);
        userRepository.save(userToUpdate);
    }

    @Override
    public User getUserByUsername(String email) {
        return userRepository
                .getUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
