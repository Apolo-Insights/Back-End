package com.sptech.produtos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Map;

@RestControllerAdvice(assignableTypes = OpenAiHairController.class)
public class OpenAiApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler({IllegalStateException.class, IOException.class})
    public ResponseEntity<Map<String, String>> handleServerError(Exception exception) {
        HttpStatus status = exception instanceof IllegalStateException ? HttpStatus.BAD_GATEWAY : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(Map.of("error", exception.getMessage()));
    }
}