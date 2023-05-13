package com.example.PP_3_1_2_spring_project_boot.service;

import com.example.PP_3_1_2_spring_project_boot.model.User;
import com.example.PP_3_1_2_spring_project_boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void updateUser(Long id, User newSwitchUser) {
        newSwitchUser.setId(id);
        userRepository.save(newSwitchUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(getUserById(userId));
    }
}