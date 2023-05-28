package ru.kata.spring.web.project.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.kata.spring.web.project.exceptions.JSONBody.UserIncorrectData;
import ru.kata.spring.web.project.exceptions.entity.NoSuchUserException;
import ru.kata.spring.web.project.exceptions.entity.UserAlreadyExistException;

import java.util.Locale;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleUserNotCreatedException(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>(new UserIncorrectData(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleNoSuchUserException(NoSuchUserException exception) {
        exception.printStackTrace();
        String errorMessage = buildErrorMessage("There is no user with ID = ", exception, " in Database");
        return new ResponseEntity<>(new UserIncorrectData(errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        exception.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        String causeMessage = exception.getCause().getMessage();
        String errorMessage = stringBuilder.append("Incorrect format ").append(Character.toLowerCase(causeMessage.charAt(0))).
                append(causeMessage.substring(1)).append(" - must be number").toString();
        return new ResponseEntity<>(new UserIncorrectData(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        exception.printStackTrace();
        String errorMessage = buildErrorMessage("User with email - '",exception,"' already exist");
        return new ResponseEntity<>(new UserIncorrectData(errorMessage), HttpStatus.BAD_REQUEST);
    }

    private String buildErrorMessage(String s1, Exception exception, String s2){
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(s1).append(exception.getMessage()).append(s2).toString();
    }
}
