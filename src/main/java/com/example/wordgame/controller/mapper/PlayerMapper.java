package com.example.wordgame.controller.mapper;

import com.example.wordgame.dto.PlayerListDTO;
import com.example.wordgame.dto.PlayerResponseDTO;
import com.example.wordgame.models.Player;
import org.springframework.data.domain.Page;

public interface PlayerMapper {

    PlayerListDTO toPlayerListDTO(Page<Player> playerPage);

    PlayerResponseDTO toPlayerResponseDTO(Player player);

}
