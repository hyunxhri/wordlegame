package com.example.wordgame.controller;

import com.example.wordgame.dto.*;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Team;
import com.example.wordgame.repository.PlayerRepository;
import com.example.wordgame.repository.TeamRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;


    public PlayerController(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping("/players")
    public PlayerListDTO getPlayers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "100") int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Player> playerPage = playerRepository.findAll(pageRequest);
        PlayerListDTO response = new PlayerListDTO();
        response.setPlayerList(playerPage.getContent().stream().map(this::toPlayerResponseDTO).toList()); // Transforma el Player en un PlayerResponseDTO, es igual que un ForEach.
        if (playerPage.hasNext()) {
            response.setNextPage(playerPage.nextPageable().getPageNumber());
        }
        return response;

    }

    private PlayerResponseDTO toPlayerResponseDTO(Player player) {
        PlayerResponseDTO dto = new PlayerResponseDTO();
        dto.setUser(player.getUser());
        dto.setImg(player.getImg());
        dto.setTeamInfo(toTeamInfoDTO(player.getTeam()));
        return dto;
    }

    private TeamInfoDTO toTeamInfoDTO(Team team) {
        TeamInfoDTO dto = null;
        if (team != null) {
            dto = new TeamInfoDTO();
            dto.setName(team.getName());
            dto.setBadge(team.getBadge());
        }
        return dto;
    }

    @GetMapping("/players/{id}")
    public PlayerResponseDTO getPlayerById(@PathVariable Long id) {
        Player player = playerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Player not found with id " + id));
        return toPlayerResponseDTO(player);
    }

    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@RequestBody CreatePlayerDTO playerDTO) {
        Player player = new Player();
        player.setUser(playerDTO.getUser());
        player.setImg(playerDTO.getImg());
        Player playerCreated = playerRepository.save(player);
        return new ResponseEntity<>(playerCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        return playerRepository.findById(id)
                .map(player -> {
                    playerRepository.delete(player);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Player not found with id " + id));
    }

    @PutMapping("/players/{id}")
    public PlayerResponseDTO updatePlayer(@PathVariable Long id, @Valid @RequestBody UpdatePlayerDTO playerDTO) {
        Player player = new Player();
        player.setId(id);
        player.setUser(playerDTO.getUser());
        player.setImg(playerDTO.getImg());
        Team team = null;
        if (playerDTO.getTeamId() != null) {
            team = teamRepository.findById(playerDTO.getTeamId()).orElseThrow(() ->
                    new ResourceNotFoundException("Team with id " + id + " doesn't exists."));
            player.setTeam(team);
        }
        player = playerRepository.save(player);
        PlayerResponseDTO response = new PlayerResponseDTO();
        response.setUser(player.getUser());
        response.setImg(player.getImg());
        response.setTeamInfo(toTeamInfoDTO(team));
        return response;
    }
}
