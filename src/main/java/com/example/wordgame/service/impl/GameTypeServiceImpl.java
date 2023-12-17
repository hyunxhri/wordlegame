package com.example.wordgame.service.impl;

import com.example.wordgame.exception.GameTypeNotFoundException;
import com.example.wordgame.models.GameType;
import com.example.wordgame.repository.GameTypeRepository;
import com.example.wordgame.service.GameTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameTypeServiceImpl implements GameTypeService {

    private final GameTypeRepository gameTypeRepository;

    public GameTypeServiceImpl(GameTypeRepository gameTypeRepository) {
        this.gameTypeRepository = gameTypeRepository;
    }


    @Override
    public GameType getGameType(Long gameTypeId) throws GameTypeNotFoundException {
        return gameTypeRepository.findById(gameTypeId).orElseThrow(() -> new GameTypeNotFoundException("GameType with id " + gameTypeId + " was not found"));
    }

    @Override
    public Page<GameType> getGameTypes(Pageable page) {
        return gameTypeRepository.findAll(page);
    }

    @Override
    public List<GameType> getGameTypes() {
        return gameTypeRepository.findAll();
    }
}
