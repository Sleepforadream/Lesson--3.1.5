package ru.kata.spring.web.project.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.web.project.dto.UserDto;
import ru.kata.spring.web.project.repository.RoleRepository;
import ru.kata.spring.web.project.repository.UserRepository;
import ru.kata.spring.web.project.model.Role;
import ru.kata.spring.web.project.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUserByUsername(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserByIdFetchRoles(id);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(getUserById(userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllUsersFetchRoles();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                (getRollsByUserId(user.getId())));
    }

    @Override
    public Set<Role> getRollsByUserId(long userId) {
        return getUserById(userId).getRoles();
    }

    @Override
    public User convertToUser(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserDto convertToUserDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }


}