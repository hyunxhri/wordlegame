package com.example.wordgame.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponseDTO {

    private String user;
    private String img;
    private TeamInfoDTO teamInfo;

}
