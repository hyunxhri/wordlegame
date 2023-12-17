package com.example.wordgame.service;

import com.example.wordgame.models.Word;
import org.springframework.data.domain.Page;

public interface WordService {

    Page<Word> getWords(Integer number, String startsWith, String contains, String endsWith);

    Word getWord(String word);

    Word createWord(String wordInList);
}
