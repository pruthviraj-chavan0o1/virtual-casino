package com.pruthviraj.virtual_casino.controller;

import com.pruthviraj.virtual_casino.entity.GameHistory;
import com.pruthviraj.virtual_casino.repository.GameHistoryRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game-history")
public class GameHistoryController {

    private final GameHistoryRepository gameHistoryRepository;

    public GameHistoryController(
            GameHistoryRepository gameHistoryRepository) {

        this.gameHistoryRepository = gameHistoryRepository;
    }

    // Get current user's game history
    @GetMapping
    public ResponseEntity<List<GameHistory>> getGameHistory(
            Authentication authentication) {

        String username = authentication.getName();

        List<GameHistory> history =
                gameHistoryRepository
                        .findByUsernameOrderByPlayedAtDesc(username);

        return ResponseEntity.ok(history);
    }
}