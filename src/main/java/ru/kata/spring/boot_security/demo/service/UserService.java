package com.example.PP_3_1_2_spring_project_boot.service;

import com.example.PP_3_1_2_spring_project_boot.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    List<User> getAllUsers();

    User getUserById(Long userId);

    void updateUser(Long id, User user);

    void deleteUser(Long userId);
}
