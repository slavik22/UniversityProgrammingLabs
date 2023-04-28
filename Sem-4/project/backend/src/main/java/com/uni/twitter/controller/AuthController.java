package com.bozzaccio.twitterclone.controller;

import com.bozzaccio.twitterclone.security.dto.LoginRequest;
import com.bozzaccio.twitterclone.security.dto.RegisterRequest;
import com.bozzaccio.twitterclone.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController implements IController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            return ResponseEntity.ok(authService.login(loginRequest));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        try {
            return ResponseEntity.ok(authService.register(registerRequest));
        } catch (Exception e) {
            return handleException(e);
        }
    }
}
