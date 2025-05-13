package ru.kata.spring.boot_security.demo.model_mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dtos.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component          
public class ControllerModelMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public ControllerModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public User convertToUser(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
    public UserDTO convertToUserDTO(User u) {
        return modelMapper.map(u, UserDTO.class);
    }
    public List<Long> extractRoleIds(UserDTO dto) {
        return (dto.getRoles() == null)
                ? Collections.emptyList()
                : dto.getRoles()
                .stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }
}
