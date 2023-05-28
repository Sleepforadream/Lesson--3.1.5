package ru.kata.spring.web.project.exceptions.entity;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}
