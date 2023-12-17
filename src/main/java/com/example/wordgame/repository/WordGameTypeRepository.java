package com.example.wordgame.repository;

import com.example.wordgame.models.WordGameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordGameTypeRepository extends JpaRepository<WordGameType, Long> {
}
