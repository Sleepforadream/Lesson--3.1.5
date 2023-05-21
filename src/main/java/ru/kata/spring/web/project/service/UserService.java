package ru.kata.spring.web.project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.web.project.model.Role;
import ru.kata.spring.web.project.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    User getUserByUsername(String username);

    void addUser(User user);

    User getUserById(Long userId);

    void updateUser(Long id, User user);

    void deleteUser(Long userId);

    List<User> getAllUsers();

    UserDetails loadUserByUsername(String username);

    Set<Role> getRollsByUserId(long roleId);
}
