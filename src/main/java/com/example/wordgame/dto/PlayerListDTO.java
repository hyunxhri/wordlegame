package com.example.wordgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerListDTO {

    private List<PlayerResponseDTO> playerList;
    private Integer nextPage;

}
