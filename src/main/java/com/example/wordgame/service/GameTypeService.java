package com.example.wordgame.service;

import com.example.wordgame.exception.GameTypeNotFoundException;
import com.example.wordgame.models.GameType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameTypeService {

    GameType getGameType(Long gameTypeId) throws GameTypeNotFoundException;

    Page<GameType> getGameTypes(Pageable page);

    List<GameType> getGameTypes();

}
