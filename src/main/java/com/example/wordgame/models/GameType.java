package com.example.wordgame.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "game_type")
public class GameType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String difficultyG;
    private String description;
    private int maxTries; // Máximos intentos

    @OneToOne
    private Game game;

    @OneToMany(mappedBy = "gameType")
    private List<WordGameType> wordGameTypes;
}
