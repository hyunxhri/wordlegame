package com.example.wordgame.controller.mapper;

import com.example.wordgame.dto.WordListDTO;
import com.example.wordgame.models.Word;
import org.springframework.data.domain.Page;

public interface WordMapper {

    WordListDTO toWordListDTO(Page<Word> wordPage);

}
