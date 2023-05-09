package com.uni.twitter.service;


import com.uni.twitter.security.UserLogged;
import com.uni.twitter.security.dto.AuthResponse;
import com.uni.twitter.security.dto.RegisterRequest;
import com.uni.twitter.security.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.uni.twitter.util.ErrorUtils.*;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtils jwtUtils,
                       PasswordEncoder encoder,
                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.userService = userService;
    }

    public ResponseEntity<?> login(com.uni.twitter.security.dto.LoginRequest request) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);

        UserLogged userLogged = (UserLogged) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthResponse(jwt,
                userLogged.getId(),
                userLogged.getUsername(),
                userLogged.getEmail()));
    }

    public ResponseEntity<?> register(RegisterRequest request) {

        if (userService.checkIfUserExistsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(buildErrorMessage(BASE_JWT_ERROR, USERNAME, ALREADY_IN_USE));
        }

        if (userService.checkIfUserExistsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(buildErrorMessage(BASE_JWT_ERROR, EMAIL, ALREADY_IN_USE));
        }

        com.uni.twitter.entity.User user = new com.uni.twitter.entity.User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        userService.create(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
