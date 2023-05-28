package ru.kata.spring.web.project.dto;

import lombok.*;
import ru.kata.spring.web.project.model.Role;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Data
public class UserDto {

    public UserDto() {}

    private Long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private Integer age;

    private Set<Role> roles = new HashSet<>();

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

}
