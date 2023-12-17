package com.example.wordgame.controller.mapper.impl;

import com.example.wordgame.controller.mapper.GameMapper;
import com.example.wordgame.dto.GameDTO;
import com.example.wordgame.dto.GameListDTO;
import com.example.wordgame.models.Game;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class GameMapperImpl implements GameMapper {

    @Override
    public GameDTO toGameDTO(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setWord(game.getWord());
        gameDTO.setScore(game.getScore());
        gameDTO.setNTry(game.getNTry());
        gameDTO.setPlayerName(game.getPlayer().getUser());
        gameDTO.setGameType(game.getGameType().getDescription());
        return gameDTO;
    }

    @Override
    public GameListDTO toGameListDTO(Page<Game> gamePage) {
        GameListDTO dto = new GameListDTO();
        dto.setGameList(gamePage.getContent().stream()
                .map(this::toGameDTO)
                .toList());
        if (gamePage.hasNext()) {
            dto.setNextPage(gamePage.nextPageable().getPageNumber());
        }
        return dto;
    }
}
