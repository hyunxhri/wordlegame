package com.example.wordgame.controller.mapper.impl;

import com.example.wordgame.controller.mapper.PlayerMapper;
import com.example.wordgame.dto.PlayerListDTO;
import com.example.wordgame.dto.PlayerResponseDTO;
import com.example.wordgame.dto.TeamInfoDTO;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapperImpl implements PlayerMapper {

    @Override
    public PlayerListDTO toPlayerListDTO(Page<Player> playerPage) {
        PlayerListDTO response = new PlayerListDTO();
        response.setPlayerList(
                playerPage.getContent().stream()
                        .map(this::toPlayerResponseDTO)
                        .toList()
        ); // Transforma el Player en un PlayerResponseDTO, es igual que un ForEach.

        if (playerPage.hasNext()) {
            response.setNextPage(playerPage.nextPageable().getPageNumber());
        }
        return response;
    }

    @Override
    public PlayerResponseDTO toPlayerResponseDTO(Player player) {
        PlayerResponseDTO dto = new PlayerResponseDTO();
        dto.setId(player.getId());
        dto.setUser(player.getUser());
        dto.setImg(player.getImg());
        if (player.getTeam() != null) {
            dto.setTeamInfo(toTeamInfoDTO(player.getTeam()));
        }
        return dto;
    }

    private TeamInfoDTO toTeamInfoDTO(Team team) {
        TeamInfoDTO dto = new TeamInfoDTO();
        dto.setName(team.getName());
        dto.setBadge(team.getBadge());
        return dto;
    }
}
