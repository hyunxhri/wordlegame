package com.example.wordgame.repository;

import com.example.wordgame.models.Wordle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordleRepository extends JpaRepository<Wordle, Long> {

}