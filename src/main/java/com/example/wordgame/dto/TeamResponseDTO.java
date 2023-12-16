package com.example.wordgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDTO {

    private String name;
    private String badge;
    private List<PlayerInfoDTO> playerInfo;

}
