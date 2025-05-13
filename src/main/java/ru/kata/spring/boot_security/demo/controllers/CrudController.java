package ru.kata.spring.boot_security.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dtos.UserDTO;
import ru.kata.spring.boot_security.demo.model_mappers.ControllerModelMapper;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class CrudController {
    private final UserService userService;
    private final ControllerModelMapper modelMapper;
    @Autowired
    public CrudController(UserService userService, ControllerModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/users")
    public List<UserDTO> getAll() {
        return userService.getUsers()
                .stream()
                .map(modelMapper::convertToUserDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/users/{id}")
    public UserDTO getOne(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return modelMapper.convertToUserDTO(user);
    }
    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO dto) {
        User user = modelMapper.convertToUser(dto);
        userService.saveUser(user, modelMapper.extractRoleIds(dto));                
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.convertToUserDTO(user));
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                          @RequestBody @Valid UserDTO dto,
                                          BindingResult br) {
        if (br.hasErrors()) {
            return ResponseEntity.badRequest().body(br.getFieldErrors());
        }
        User user = modelMapper.convertToUser(dto);
        userService.updateUser(id, user, modelMapper.extractRoleIds(dto));
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUser(userService.getUserById(id));
    }
    @GetMapping("/roles")
    public List<Role> getRoles() {
        return userService.getRoles();
    }
}
