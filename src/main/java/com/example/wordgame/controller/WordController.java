package com.example.wordgame.controller;


import com.example.wordgame.controller.mapper.WordMapper;
import com.example.wordgame.dto.WordListDTO;
import com.example.wordgame.models.Word;
import com.example.wordgame.service.WordGameTypeService;
import com.example.wordgame.service.WordService;
import com.example.wordgame.utils.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WordController {

    private final WordService wordService;
    private final WordGameTypeService wordGameTypeService;
    private final WordMapper wordMapper;

    public WordController(WordService wordService, WordMapper wordMapper, WordGameTypeService wordGameTypeService) {
        this.wordService = wordService;
        this.wordMapper = wordMapper;
        this.wordGameTypeService = wordGameTypeService;
    }

    @GetMapping("/words")
    public WordListDTO getWords(@RequestParam Integer number, @RequestParam(required = false) String startsWith,
                                @RequestParam(required = false) String contains, @RequestParam(required = false) String endsWith) {

        Page<Word> words = wordService.getWords(number, startsWith, contains, endsWith);
        return wordMapper.toWordListDTO(words);

    }

    @PostMapping("/words")
    public void uploadWords(@RequestParam("file") MultipartFile file) throws IOException {

        List<String> words = IOUtils.toList(file.getInputStream());
        wordGameTypeService.createWords(words);

    }


}
