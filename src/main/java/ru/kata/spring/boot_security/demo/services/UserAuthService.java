package ru.kata.spring.boot_security.demo.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.securities.UserAuthDetails;
import java.util.Optional;
@Service
public class UserAuthService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByEmailWithRoles(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        return new UserAuthDetails(user.get());
    }
    public Optional<User> findOptionalByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
