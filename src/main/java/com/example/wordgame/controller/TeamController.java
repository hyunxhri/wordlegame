package com.example.wordgame.controller;

import com.example.wordgame.controller.mapper.TeamMapper;
import com.example.wordgame.dto.CreateTeamDTO;
import com.example.wordgame.dto.TeamListDTO;
import com.example.wordgame.dto.TeamResponseDTO;
import com.example.wordgame.dto.UpdateTeamDTO;
import com.example.wordgame.exception.TeamNotFoundException;
import com.example.wordgame.models.Team;
import com.example.wordgame.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    public TeamController(TeamService teamService, TeamMapper teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @GetMapping("/teams")
    public TeamListDTO getTeams(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "100") int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Team> teamPage = teamService.getTeams(pageRequest);
        return teamMapper.toTeamListDTO(teamPage);

    }

    @GetMapping("/teams/{id}")
    public TeamResponseDTO getTeamById(@PathVariable Long id) throws TeamNotFoundException {
        return teamMapper.toTeamResponseDTO(teamService.getTeam(id));
    }

    @PostMapping("/teams")
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody CreateTeamDTO teamDTO) {

        Team team = teamService.createTeam(teamDTO.getName(), teamDTO.getBadge());
        return new ResponseEntity<>(teamMapper.toTeamResponseDTO(team), HttpStatus.CREATED);

    }

    @DeleteMapping("/teams/{id}")
    public void deleteTeam(@PathVariable Long id) throws TeamNotFoundException {
        teamService.deleteTeam(id);
    }

    @PutMapping("/teams/{id}")
    public TeamResponseDTO updateTeam(@PathVariable Long id, @Valid @RequestBody UpdateTeamDTO teamDTO) {
        Team team = teamService.updateTeam(id, teamDTO.getName(), teamDTO.getBadge());
        return teamMapper.toTeamResponseDTO(team);
    }

}
