package com.example.wordgame.controller.mapper;

import com.example.wordgame.dto.GameDTO;
import com.example.wordgame.dto.GameListDTO;
import com.example.wordgame.models.Game;
import org.springframework.data.domain.Page;

public interface GameMapper {

    GameDTO toGameDTO(Game game);

    GameListDTO toGameListDTO(Page<Game> gamePage);
}
