package ru.kata.spring.web.project.exceptions.JSONBody;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Data
@NoArgsConstructor
public class UserIncorrectData {

    public UserIncorrectData(String info) {
        this.errorInfo = info;
    }

    private String errorInfo;
    private String errorTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),
            ZoneId.systemDefault()).toString();
}

