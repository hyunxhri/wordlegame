package com.example.wordgame.service.impl;

import com.example.wordgame.exception.TeamNotFoundException;
import com.example.wordgame.models.Team;
import com.example.wordgame.repository.TeamRepository;
import com.example.wordgame.service.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team getTeam(Long teamId) throws TeamNotFoundException {
        return teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team with id " + teamId + " was not found"));
    }

    @Override
    public Page<Team> getTeams(Pageable page) {
        return teamRepository.findAll(page);
    }

    @Override
    public Team createTeam(String name, String badge) {
        Team team = new Team();
        team.setName(name);
        team.setBadge(badge);
        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Long teamId, String name, String badge) {

        Team team = teamRepository.findById(teamId).orElseGet(Team::new);
        team.setId(teamId);
        team.setName(name);
        team.setBadge(badge);
        return teamRepository.save(team);

    }

    @Override
    public void deleteTeam(Long teamId) throws TeamNotFoundException {
        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isPresent()) {
            teamRepository.deleteById(teamId);
        } else throw new TeamNotFoundException("Team with id " + teamId + " was not found");
    }

}
