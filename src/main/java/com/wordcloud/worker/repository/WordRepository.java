package com.wordcloud.worker.repository;

import com.wordcloud.worker.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
    Word findByWord(String word);
}
