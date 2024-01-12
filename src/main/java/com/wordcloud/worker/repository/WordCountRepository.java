package com.wordcloud.worker.repository;

import com.wordcloud.worker.model.WordCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WordCountRepository extends JpaRepository<WordCount, UUID> {
}
