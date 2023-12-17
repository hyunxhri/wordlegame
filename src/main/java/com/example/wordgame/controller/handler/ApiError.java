package com.example.wordgame.controller.handler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

    private String message;

}
