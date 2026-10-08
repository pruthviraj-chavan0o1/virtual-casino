package com.pruthviraj.virtual_casino.controller;

import com.pruthviraj.virtual_casino.entity.User;
import com.pruthviraj.virtual_casino.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final UserRepository userRepository;

    public WalletController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get current user's virtual coin balance
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(Authentication authentication) {

        String username = authentication.getName();

        var userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        User user = userOptional.get();

        return ResponseEntity.ok(
                "Your virtual coin balance is: "
                        + user.getVirtualCoins()
        );
    }
}