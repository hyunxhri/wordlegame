package com.example.wordgame.service.impl;

import com.example.wordgame.exception.GameNotFoundException;
import com.example.wordgame.exception.GameTypeNotFoundException;
import com.example.wordgame.exception.PlayerNotFoundException;
import com.example.wordgame.models.Game;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Team;
import com.example.wordgame.repository.GameRepository;
import com.example.wordgame.service.GameService;
import com.example.wordgame.service.GameTypeService;
import com.example.wordgame.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final GameTypeService gameTypeService;

    public GameServiceImpl(GameRepository gameRepository, PlayerService playerService, GameTypeService gameTypeService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.gameTypeService = gameTypeService;
    }


    @Override
    public Game getGame(Long gameId) throws GameNotFoundException {
        return gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game with id " + gameId + " was not found"));
    }

    @Override
    public Page<Game> getGames(Pageable page) {
        return gameRepository.findAll(page);
    }

    @Override
    public Game createGame(String word, Long playerId, Long gameTypeId) {
        Game game = new Game();

        try {
            game.setGameType(gameTypeService.getGameType(gameTypeId));
        } catch (GameTypeNotFoundException e) {
            throw new IllegalArgumentException("GameType with id " + gameTypeId + " was not found");
        }

        try {
            game.setPlayer(playerService.getPlayer(playerId));
        } catch (PlayerNotFoundException e) {
            throw new IllegalArgumentException("Player with id " + playerId + " was not found");
        }

        game.setDatetime(new Date());
        game.setScore(0);
        game.setNTry(0);
        game.setWord(word);
        return gameRepository.save(game);
    }

    @Override
    public Game updateGame(Long gameId, int score, int nTry) {

        Game game = gameRepository.findById(gameId).orElseGet(Game::new);
        game.setScore(score);
        game.setNTry(nTry);
        Player player = game.getPlayer();
        player.setScore(player.getScore() + score);
        if (player.getTeam() != null) {
            Team team = player.getTeam();
            team.setScore(team.getScore() + score);
        }
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(Long gameId) throws GameNotFoundException {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            gameRepository.deleteById(gameId);
        } else throw new GameNotFoundException("Game with id " + gameId + " was not found");
    }
}
