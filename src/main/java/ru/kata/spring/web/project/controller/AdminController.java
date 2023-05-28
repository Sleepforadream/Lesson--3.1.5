package ru.kata.spring.web.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.web.project.dto.UserDto;
import ru.kata.spring.web.project.exceptions.entity.NoSuchUserException;
import ru.kata.spring.web.project.exceptions.entity.UserAlreadyExistException;
import ru.kata.spring.web.project.exceptions.entity.UserNotSaveException;
import ru.kata.spring.web.project.model.User;
import ru.kata.spring.web.project.service.RoleService;
import ru.kata.spring.web.project.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping({"", "/users"})
    public ResponseEntity<List<UserDto>> viewAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> usersDto = users.stream().map(userService::convertToUserDto).toList();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> viewUserProfile(@PathVariable Long id) {
        UserDto userDto = userService.convertToUserDto(userService.getUserById(id));
        if (userDto == null) throw new NoSuchUserException(id.toString());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        throwErrorFromValid(bindingResult);
        User user = userService.convertToUser(userDto);
        try {
            userService.saveUser(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistException(user.getEmail());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("/users")
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        throwErrorFromValid(bindingResult);
        userService.saveUser(userService.convertToUser(userDto));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) throw new NoSuchUserException(id.toString());
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void throwErrorFromValid(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append("Error in field '").append(error.getField())
                        .append("' - ").append(error.getDefaultMessage()).append(";");
            }
            throw new UserNotSaveException(errorMsg.toString());
        }
    }
}
