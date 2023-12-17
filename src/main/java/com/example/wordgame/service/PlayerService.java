package com.example.wordgame.service;

import com.example.wordgame.exception.PlayerNotFoundException;
import com.example.wordgame.models.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlayerService {

    Player getPlayer(Long playerId) throws PlayerNotFoundException;

    Page<Player> getPlayers(Pageable page);

    Player createPlayer(String user, String img);

    Player updatePlayer(Long playerId, String user, String img, Long teamId);

    void deletePlayer(Long playerId) throws PlayerNotFoundException;

}
