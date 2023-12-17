package com.example.wordgame.exception;

public class GameNotFoundException extends Exception {

    public GameNotFoundException(String message) {
        super(message); // Llama al constructor del padre (Exception) que recibe un String (el mensaje)
    }
}
