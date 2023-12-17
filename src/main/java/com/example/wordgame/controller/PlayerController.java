package com.example.wordgame.controller;

import com.example.wordgame.controller.mapper.PlayerMapper;
import com.example.wordgame.dto.CreatePlayerDTO;
import com.example.wordgame.dto.PlayerListDTO;
import com.example.wordgame.dto.PlayerResponseDTO;
import com.example.wordgame.dto.UpdatePlayerDTO;
import com.example.wordgame.exception.PlayerNotFoundException;
import com.example.wordgame.models.Player;
import com.example.wordgame.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;


    public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @GetMapping("/players")
    public PlayerListDTO getPlayers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "100") int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Player> playerPage = playerService.getPlayers(pageRequest);
        return playerMapper.toPlayerListDTO(playerPage);

    }

    @GetMapping("/players/{id}")
    public PlayerResponseDTO getPlayerById(@PathVariable Long id) throws PlayerNotFoundException {

        Player player = playerService.getPlayer(id);
        return playerMapper.toPlayerResponseDTO(player);

    }

    @PostMapping("/players")
    public ResponseEntity<PlayerResponseDTO> createPlayer(@RequestBody CreatePlayerDTO playerDTO) {

        Player player = playerService.createPlayer(playerDTO.getUser(), playerDTO.getImg());
        return new ResponseEntity<>(playerMapper.toPlayerResponseDTO(player), HttpStatus.CREATED);

    }

    @PutMapping("/players/{id}")
    public PlayerResponseDTO updatePlayer(@PathVariable Long id, @Valid @RequestBody UpdatePlayerDTO playerDTO) {

        Player player = playerService.updatePlayer(id, playerDTO.getUser(), playerDTO.getImg(), playerDTO.getTeamId());
        return playerMapper.toPlayerResponseDTO(player);

    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) throws PlayerNotFoundException {

        playerService.deletePlayer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
