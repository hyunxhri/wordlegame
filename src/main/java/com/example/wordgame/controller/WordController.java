package com.example.wordgame.controller;


import com.example.wordgame.dto.WordListDto;
import com.example.wordgame.models.Word;
import com.example.wordgame.repository.WordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WordController {

    private final WordRepository wordRepository;

    public WordController(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }

    @GetMapping("/words")
    public ResponseEntity<WordListDto> getWords(@RequestParam Integer number, @RequestParam(required = false) String startsWith,
                                                @RequestParam(required = false) String contains, @RequestParam(required = false) String endsWith){

        Pageable page = PageRequest.of(0, number);
        Specification<Word> spec = WordRepository.isNotNull();
        spec = startsWith != null ? spec.and(WordRepository.startsWith(startsWith)) : spec;
        spec = contains != null ? spec.and(WordRepository.contains(contains)) : spec;
        spec = endsWith != null ? spec.and(WordRepository.endsWith(endsWith)) : spec;
        Page<Word> words = wordRepository.findAll(spec, page);
        return ResponseEntity.status(HttpStatus.OK).body(toWordList(words.getContent()));

    }

    private WordListDto toWordList(List<Word> words){

        WordListDto wordList = new WordListDto();
        List<String> wordsString = words.stream()
                .map(Word::getWord)
                .toList();
        wordList.setWords(wordsString);
        return  wordList;

    }


}
