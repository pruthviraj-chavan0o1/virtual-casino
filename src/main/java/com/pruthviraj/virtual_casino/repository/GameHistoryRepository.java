package com.pruthviraj.virtual_casino.repository;

import com.pruthviraj.virtual_casino.entity.GameHistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameHistoryRepository
        extends JpaRepository<GameHistory, Long> {

    List<GameHistory> findByUsernameOrderByPlayedAtDesc(String username);
}