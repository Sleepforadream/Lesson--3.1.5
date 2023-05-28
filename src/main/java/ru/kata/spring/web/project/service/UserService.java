package ru.kata.spring.web.project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.web.project.dto.UserDto;
import ru.kata.spring.web.project.model.Role;
import ru.kata.spring.web.project.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUserByUsername(String username);

    void saveUser(User user);

    User getUserById(Long userId);

    void deleteUser(Long userId);

    UserDetails loadUserByUsername(String username);

    Set<Role> getRollsByUserId(long roleId);

    User convertToUser(UserDto userDto);

    UserDto convertToUserDto(User user);
}
