package com.example.wordgame.repository;

import com.example.wordgame.models.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface GameTypeRepository extends JpaRepository<GameType, Long> {
}
