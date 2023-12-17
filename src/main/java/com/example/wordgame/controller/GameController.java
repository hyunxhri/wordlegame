package com.example.wordgame.controller;

import com.example.wordgame.controller.mapper.GameMapper;
import com.example.wordgame.dto.CreateGameDTO;
import com.example.wordgame.dto.GameDTO;
import com.example.wordgame.dto.GameListDTO;
import com.example.wordgame.dto.UpdateGameDTO;
import com.example.wordgame.exception.GameNotFoundException;
import com.example.wordgame.models.Game;
import com.example.wordgame.service.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    public GameController(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @GetMapping("/games")
    public GameListDTO getGames(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "100") int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Game> gamePage = gameService.getGames(pageRequest);
        return gameMapper.toGameListDTO(gamePage);
    }

    @GetMapping("/games/{id}")
    public GameDTO getGame(@PathVariable Long id) throws GameNotFoundException {
        return gameMapper.toGameDTO(gameService.getGame(id));
    }

    @PostMapping("/games")
    public void createGame(@RequestBody CreateGameDTO createGameDTO) {
        gameService.createGame(createGameDTO.getWord(), createGameDTO.getPlayerId(), createGameDTO.getGameTypeId());
    }

    @PutMapping("/games/{id}")
    public void updateGame(@PathVariable Long id, @RequestBody UpdateGameDTO updateGameDTO) {
        gameService.updateGame(id, updateGameDTO.getScore(), updateGameDTO.getNt());
    }
}
