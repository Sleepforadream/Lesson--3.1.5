package ru.kata.spring.web.project.exceptions.entity;

public class UserNotSaveException extends RuntimeException {
    public UserNotSaveException(String message) {
        super(message);
    }
}
