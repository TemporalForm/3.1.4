package ru.kata.spring.boot_security.demo.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.kata.spring.boot_security.demo.models.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
public class UserDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotEmpty(message = "You must enter your firstname")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String firstname;
    @NotEmpty(message = "You must enter your lastname")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String lastname;
    @NotNull(message = "You must select your age")
    @Min(value = 1, message = "You must be older than 1 and younger than 127")
    private Byte age;
    @NotEmpty(message = "You must enter your email")
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "You must set your password")
    @Size(min = 6, message = "Password should be longer than 6 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Collection<Role> roles;
    public UserDTO() {
    }
    public UserDTO(Long id, String firstname, String lastname, Byte age, String email, String password, Collection<Role> roles) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public Byte getAge() {
        return age;
    }
    public void setAge(Byte age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Collection<Role> getRoles() {
        return roles;
    }
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
