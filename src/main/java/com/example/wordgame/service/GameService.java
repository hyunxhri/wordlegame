package com.example.wordgame.service;

import com.example.wordgame.exception.GameNotFoundException;
import com.example.wordgame.models.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameService {

    Game getGame(Long gameId) throws GameNotFoundException;

    Page<Game> getGames(Pageable page);

    Game createGame(String word, Long playerId, Long gameTypeId);

    Game updateGame(Long gameId, int score, int nTry);

    void deleteGame(Long gameId) throws GameNotFoundException;

}
