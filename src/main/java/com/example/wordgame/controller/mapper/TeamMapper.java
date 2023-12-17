package com.example.wordgame.controller.mapper;

import com.example.wordgame.dto.TeamListDTO;
import com.example.wordgame.dto.TeamResponseDTO;
import com.example.wordgame.models.Team;
import org.springframework.data.domain.Page;

public interface TeamMapper {

    TeamListDTO toTeamListDTO(Page<Team> teamPage);

    TeamResponseDTO toTeamResponseDTO(Team player);

}
