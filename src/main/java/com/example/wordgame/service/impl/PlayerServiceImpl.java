package com.example.wordgame.service.impl;

import com.example.wordgame.exception.PlayerNotFoundException;
import com.example.wordgame.exception.TeamNotFoundException;
import com.example.wordgame.models.Player;
import com.example.wordgame.repository.PlayerRepository;
import com.example.wordgame.service.PlayerService;
import com.example.wordgame.service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final TeamService teamService;
    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(TeamService teamService, PlayerRepository playerRepository) {
        this.teamService = teamService;
        this.playerRepository = playerRepository;
    }


    @Override
    public Player getPlayer(Long playerId) throws PlayerNotFoundException {
        return playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException("Player with id " + playerId + " was not found"));
    }

    @Override
    public Page<Player> getPlayers(Pageable page) {
        return playerRepository.findAll(page);
    }

    @Override
    public Player createPlayer(String user, String img) {

        Player player = new Player();
        player.setUser(user);
        player.setImg(img);
        return playerRepository.save(player);

    }

    @Override
    public Player updatePlayer(Long playerId, String user, String img, Long teamId) {

        Player player = playerRepository.findById(playerId).orElseGet(Player::new);
        player.setUser(user);
        player.setImg(img);
        try {
            player.setTeam(teamId != null ? teamService.getTeam(teamId) : null);
        } catch (TeamNotFoundException e) {
            throw new IllegalArgumentException("Team with id " + teamId + "does not exists");
        }

        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Long playerId) throws PlayerNotFoundException {
        Optional<Player> player = playerRepository.findById(playerId);
        if (player.isPresent()) {
            playerRepository.deleteById(playerId);
        } else throw new PlayerNotFoundException("Player with id " + playerId + " was not found");

    }
}
