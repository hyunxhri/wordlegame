package com.example.wordgame.dto;

import com.example.wordgame.models.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamListDTO {

    private List<Team> teamList;
    private Integer nextPage;

}
