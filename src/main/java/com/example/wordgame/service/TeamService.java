package com.example.wordgame.service;

import com.example.wordgame.exception.TeamNotFoundException;
import com.example.wordgame.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {

    Team getTeam(Long teamId) throws TeamNotFoundException;

    Page<Team> getTeams(Pageable page);

    Team createTeam(String name, String badge);

    Team updateTeam(Long teamId, String name, String badge);

    void deleteTeam(Long teamId) throws TeamNotFoundException;

}
