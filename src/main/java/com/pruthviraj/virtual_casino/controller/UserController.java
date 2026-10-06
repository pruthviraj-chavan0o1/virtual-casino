
package com.pruthviraj.virtual_casino.controller;

import com.pruthviraj.virtual_casino.dto.LoginRequest;
import com.pruthviraj.virtual_casino.dto.LoginResponse;
import com.pruthviraj.virtual_casino.dto.RegisterRequest;
import com.pruthviraj.virtual_casino.dto.RegisterResponse;
import com.pruthviraj.virtual_casino.entity.User;
import com.pruthviraj.virtual_casino.repository.UserRepository;
import com.pruthviraj.virtual_casino.service.JwtService;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        String hashedPassword =
                passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                hashedPassword
        );

        try {
            User savedUser = userRepository.saveAndFlush(user);

            RegisterResponse response = new RegisterResponse(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getEmail(),
                    savedUser.getVirtualCoins(),
                    "Registration successful"
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);

        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username or email already exists");
        }
    }

    // Log in and generate a JWT token
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @Valid @RequestBody LoginRequest request) {

        var userOptional =
                userRepository.findByUsername(request.getUsername());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getVirtualCoins(),
                token,
                "Login successful"
        );

        return ResponseEntity.ok(response);
    }
}
