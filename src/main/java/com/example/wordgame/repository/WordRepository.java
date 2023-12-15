package com.example.wordgame.repository;

import com.example.wordgame.models.Word;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {


    static Specification<Word> startsWith(String prefix) {
        return (word, cq, cb) -> cb.like(word.get("word"), prefix + "%");
    }

    static Specification<Word> contains(String substring) {
        return (word, cq, cb) -> cb.like(word.get("word"), "%" + substring + "%");
    }

    static Specification<Word> endsWith(String suffix) {
        return (word, cq, cb) -> cb.like(word.get("word"), "%" + suffix);
    }

    static Specification<Word> isNotNull() {
        return (word, cq, cb) -> cb.isNotNull(word.get("word"));
    }

}