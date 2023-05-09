package com.uni.twitter.controller;

import com.uni.twitter.security.JWTAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.EntityNotFoundException;

@CrossOrigin(origins = "*", maxAge = 3600)
public interface IController {

    default ResponseEntity<?> handleException(Exception exception) {

        if (exception instanceof IllegalArgumentException)
            return ResponseEntity.badRequest().body(exception.getMessage());

        if (exception instanceof EntityNotFoundException)
            return ResponseEntity.notFound().build();

        if (exception instanceof JWTAuthException)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());

        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
