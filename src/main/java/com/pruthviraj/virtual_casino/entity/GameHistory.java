package com.pruthviraj.virtual_casino.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_history")
public class GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String game;

    private String choice;

    private String result;

    private long bet;

    private boolean won;

    private long balanceAfter;

    private LocalDateTime playedAt;

    public GameHistory() {
    }

    public GameHistory(
            String username,
            String game,
            String choice,
            String result,
            long bet,
            boolean won,
            long balanceAfter) {

        this.username = username;
        this.game = game;
        this.choice = choice;
        this.result = result;
        this.bet = bet;
        this.won = won;
        this.balanceAfter = balanceAfter;
        this.playedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getGame() {
        return game;
    }

    public String getChoice() {
        return choice;
    }

    public String getResult() {
        return result;
    }

    public long getBet() {
        return bet;
    }

    public boolean isWon() {
        return won;
    }

    public long getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }
}