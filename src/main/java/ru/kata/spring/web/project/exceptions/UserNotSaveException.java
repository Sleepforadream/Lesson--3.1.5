package ru.kata.spring.web.project.exceptions;

public class UserNotSaveException extends RuntimeException {
    public UserNotSaveException(String message) {
        super(message);
    }
}
