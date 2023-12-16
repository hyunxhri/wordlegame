package com.example.wordgame.repository;

import com.example.wordgame.models.GameType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordleRepository extends JpaRepository<GameType, Long> {

}