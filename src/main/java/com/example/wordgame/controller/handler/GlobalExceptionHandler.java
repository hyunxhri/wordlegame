package com.example.wordgame.controller.handler;

import com.example.wordgame.exception.GameNotFoundException;
import com.example.wordgame.exception.GameTypeNotFoundException;
import com.example.wordgame.exception.PlayerNotFoundException;
import com.example.wordgame.exception.TeamNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class) // Si salta excepción de playernotfoundexc, lo maneja este método
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handlePlayerNotFoundException(PlayerNotFoundException exception) {
        return ApiError.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleTeamNotFoundException(TeamNotFoundException exception) {
        return ApiError.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleGameNotFoundException(GameNotFoundException exception) {
        return ApiError.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(GameTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleGameTypeNotFoundException(GameTypeNotFoundException exception) {
        return ApiError.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleIllegalArgumentException(IllegalArgumentException exception) {
        return ApiError.builder()
                .message(exception.getMessage())
                .build();
    }


}
