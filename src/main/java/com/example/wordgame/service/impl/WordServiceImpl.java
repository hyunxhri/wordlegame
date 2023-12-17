package com.example.wordgame.service.impl;

import com.example.wordgame.models.Word;
import com.example.wordgame.repository.WordRepository;
import com.example.wordgame.service.WordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl implements WordService {

    private WordRepository wordRepository;

    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }


    @Override
    public Page<Word> getWords(Integer number, String startsWith, String contains, String endsWith) {

        Pageable page = PageRequest.of(0, number);

        Specification<Word> spec = WordRepository.isNotNull();
        spec = startsWith != null ? spec.and(WordRepository.startsWith(startsWith)) : spec;
        spec = contains != null ? spec.and(WordRepository.contains(contains)) : spec;
        spec = endsWith != null ? spec.and(WordRepository.endsWith(endsWith)) : spec;
        return wordRepository.findAll(spec, page);
    }

    @Override
    public Word getWord(String word) {
        return null;
    }

    @Override
    public Word createWord(String wordString) {
        Word word = new Word();
        word.setWord(wordString);
        return wordRepository.save(word);
    }

}
