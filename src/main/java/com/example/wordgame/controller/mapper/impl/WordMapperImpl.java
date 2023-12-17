package com.example.wordgame.controller.mapper.impl;

import com.example.wordgame.controller.mapper.WordMapper;
import com.example.wordgame.dto.WordListDTO;
import com.example.wordgame.models.Word;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WordMapperImpl implements WordMapper {

    @Override
    public WordListDTO toWordListDTO(Page<Word> wordPage) {
        WordListDTO response = new WordListDTO();
        List<String> wordsString = wordPage.getContent().stream()
                .map(Word::getWord)
                .toList();
        response.setWords(wordsString);
        if (wordPage.hasNext()) {
            response.setNextPage(wordPage.nextPageable().getPageNumber());
        }
        return response;
    }
}
