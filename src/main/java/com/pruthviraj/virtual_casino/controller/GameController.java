package com.pruthviraj.virtual_casino.controller;

import com.pruthviraj.virtual_casino.entity.GameHistory;
import com.pruthviraj.virtual_casino.entity.User;
import com.pruthviraj.virtual_casino.repository.GameHistoryRepository;
import com.pruthviraj.virtual_casino.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final UserRepository userRepository;
    private final GameHistoryRepository gameHistoryRepository;
    private final Random random = new Random();

    public GameController(
            UserRepository userRepository,
            GameHistoryRepository gameHistoryRepository) {

        this.userRepository = userRepository;
        this.gameHistoryRepository = gameHistoryRepository;
    }

    // Coin Flip Game
    @PostMapping("/coin-flip")
    public ResponseEntity<?> coinFlip(
            Authentication authentication,
            @RequestParam String choice,
            @RequestParam long bet) {

        String username = authentication.getName();

        var userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        User user = userOptional.get();

        // Validate choice
        if (!choice.equalsIgnoreCase("HEADS")
                && !choice.equalsIgnoreCase("TAILS")) {

            return ResponseEntity.badRequest()
                    .body("Choice must be HEADS or TAILS");
        }

        // Validate bet
        if (bet <= 0) {
            return ResponseEntity.badRequest()
                    .body("Bet must be greater than 0");
        }

        // Check balance
        if (bet > user.getVirtualCoins()) {
            return ResponseEntity.badRequest()
                    .body("Insufficient virtual coins");
        }

        // Generate result
        String result = random.nextBoolean()
                ? "HEADS"
                : "TAILS";

        boolean won = choice.equalsIgnoreCase(result);

        // Update virtual coins
        if (won) {
            user.setVirtualCoins(
                    user.getVirtualCoins() + bet
            );
        } else {
            user.setVirtualCoins(
                    user.getVirtualCoins() - bet
            );
        }

        userRepository.save(user);

        // Save game history
        GameHistory history = new GameHistory(
                username,
                "COIN_FLIP",
                choice.toUpperCase(),
                result,
                bet,
                won,
                user.getVirtualCoins()
        );

        gameHistoryRepository.save(history);

        String message;

        if (won) {
            message = "You won!";
        } else {
            message = "You lost!";
        }

        return ResponseEntity.ok(
                "Result: " + result
                        + " | " + message
                        + " | Balance: "
                        + user.getVirtualCoins()
        );
    }
}