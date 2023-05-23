package ru.kata.spring.web.project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.web.project.model.Role;
import ru.kata.spring.web.project.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUserByUsername(String username);

    void addUser(User user);

    User getUserById(Long userId);

    void updateUser(User user);

    void deleteUser(Long userId);

    UserDetails loadUserByUsername(String username);

    Set<Role> getRollsByUserId(long roleId);
}
