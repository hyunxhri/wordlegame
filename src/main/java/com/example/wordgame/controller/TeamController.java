package com.example.wordgame.controller;

import com.example.wordgame.dto.*;
import com.example.wordgame.models.Player;
import com.example.wordgame.models.Team;
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
public class TeamController {

    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping("/teams")
    public TeamListDTO getTeams(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "100") int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Team> teamPage = teamRepository.findAll(pageRequest);
        TeamListDTO response = new TeamListDTO();
        response.setTeamList(teamPage.getContent());
        if (teamPage.hasNext()) {
            response.setNextPage(teamPage.nextPageable().getPageNumber());
        }
        return response;
    }

    @GetMapping("/teams/{id}")
    public Team getTeamById(@PathVariable Long id) {
        return teamRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Team not found with id " + id));
    }

    @PostMapping("/teams")
    public ResponseEntity<?> createTeam(@RequestBody CreateTeamDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.getName());
        team.setBadge(teamDTO.getBadge());
        Team teamCreated = teamRepository.save(team);
        return new ResponseEntity<>(teamCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(team -> {
                    teamRepository.delete(team);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + id));
    }

    @PutMapping("/teams/{id}")
    public TeamResponseDTO updateTeam(@PathVariable Long id, @Valid @RequestBody UpdateTeamDTO teamDTO) {
        Team team = new Team();
        team.setId(id);
        team.setName(teamDTO.getName());
        team.setBadge(teamDTO.getBadge());
        team = teamRepository.save(team);
        TeamResponseDTO response = new TeamResponseDTO();
        response.setName(team.getName());
        response.setBadge(team.getBadge());
        if (!team.getPlayers().isEmpty()) {
            response.setPlayerInfo(team.getPlayers().stream().map(this::toPlayerInfoDTO).toList());
        }
        return response;
    }

    private PlayerInfoDTO toPlayerInfoDTO(Player player) {
        PlayerInfoDTO dto = new PlayerInfoDTO();
        dto.setUser(player.getUser());
        dto.setScore(player.getScore());
        return dto;
    }
}
