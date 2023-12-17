package com.example.wordgame.service.impl;

import com.example.wordgame.models.GameType;
import com.example.wordgame.models.Word;
import com.example.wordgame.models.WordGameType;
import com.example.wordgame.repository.WordGameTypeRepository;
import com.example.wordgame.service.GameTypeService;
import com.example.wordgame.service.WordGameTypeService;
import com.example.wordgame.service.WordService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WordGameTypeServiceImpl implements WordGameTypeService {

    private final WordGameTypeRepository wordGameTypeRepository;
    private final WordService wordService;
    private final GameTypeService gameTypeService;


    public WordGameTypeServiceImpl(WordGameTypeRepository wordGameTypeRepository, WordService wordService, GameTypeService gameTypeService) {
        this.wordGameTypeRepository = wordGameTypeRepository;
        this.wordService = wordService;
        this.gameTypeService = gameTypeService;
    }

    @Override
    public void createWords(List<String> words) {

        Word word;
        List<GameType> gameTypes = gameTypeService.getGameTypes();

        for (String wordInList : words) {
            word = wordService.createWord(wordInList);
            for (GameType gameType : gameTypes) {
                WordGameType wordGameType = new WordGameType();
                wordGameType.setWord(word);
                wordGameType.setGameType(gameType);
                wordGameType.setWordDifficulty(mapToString(calculateDifficulty(word, gameType)));
                wordGameTypeRepository.save(wordGameType);

            }
        }


    }

    private Integer calculateDifficulty(Word word, GameType gameType) {
        String wordString = word.getWord();
        int wordLength = wordString.length();
        Map<Character, Integer> repetitions = new HashMap<>(); //Mapeo el número de veces que se repite cada caracter

        for (char character : wordString.toCharArray()) {
            repetitions.put(character, repetitions.getOrDefault(character, 0) + 1);
        }

        List<Integer> sortedRepetitions = repetitions.values().stream().sorted(Comparator.reverseOrder()).toList(); // Ordeno de mayor a menor
        int maxIndex = Math.min(sortedRepetitions.size(), 3); // Coge 3 caracteres más repetidos y si no hay 3, que coja el número máximo de caracteres repetidos que haya.
        int repetitionsValue = 0;
        for (int i = 0; i < maxIndex; i++) {
            repetitionsValue += sortedRepetitions.get(i);
        }

        int difficulty = 0;

        switch (gameType.getDescription()) {
            case "Wordle":
                difficulty = wordLength + Math.round(10 * ((float) repetitionsValue / (float) wordLength));
                break;
            case "Ahorcado":
                difficulty = wordLength - Math.round(10 * (float) repetitionsValue / (float) wordLength);
                break;
        }

        System.out.println("Word " + word + " have " + difficulty + " for game " + gameType.getDescription());
        return Math.max(difficulty, 0);
    }

    private String mapToString(Integer difficulty) {
        if (difficulty < 10) {
            return "EASY";
        } else if (difficulty < 15) {
            return "MEDIUM";
        } else return "HARD";
    }

}
