package com.example.wordgame.controller.mapper.impl;

import com.example.wordgame.controller.mapper.TeamMapper;
import com.example.wordgame.dto.PlayerInfoDTO;
import com.example.wordgame.dto.TeamListDTO;
import com.example.wordgame.dto.TeamResponseDTO;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class TeamMapperImpl implements TeamMapper {

    public TeamListDTO toTeamListDTO(Page<Team> teamPage) {

        TeamListDTO response = new TeamListDTO();
        response.setTeamList(
                teamPage.getContent().stream()
                        .map(this::toTeamResponseDTO)
                        .toList()
        );

        if (teamPage.hasNext()) {
            response.setNextPage(teamPage.nextPageable().getPageNumber());
        }
        return response;

    }

    @Override
    public TeamResponseDTO toTeamResponseDTO(Team team) {
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setName(team.getName());
        dto.setBadge(team.getBadge());
        if (team.getPlayers() != null) {
            dto.setPlayerInfo(team.getPlayers().stream().map(this::toPlayerInfoDTO).toList());
        }
        return dto;
    }

    private PlayerInfoDTO toPlayerInfoDTO(Player player) {
        PlayerInfoDTO playerInfo = new PlayerInfoDTO();
        playerInfo.setUser(player.getUser());
        player.setImg(player.getImg());
        return playerInfo;
    }
}
