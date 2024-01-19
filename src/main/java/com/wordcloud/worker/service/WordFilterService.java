package com.wordcloud.worker.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WordFilterService {

    private static final String STOP_WORDS_FILE_PATH = "src/main/resources/filter/stop_words.txt";

    public List<String> filter(List<String> words, boolean filterCommon, String customWords) {
        Set<String> customWordsSet;

        if (!customWords.isEmpty()) {
            customWordsSet = parseCustomWords(customWords);
            words.removeAll(customWordsSet);
        }

        if (filterCommon) {
            Set<String> stopWords = getStopWords();
            words.removeAll(stopWords);
        }
        return words;
    }

    private Set<String> parseCustomWords(String customWords) {
        return Arrays.stream(customWords.split(",\\s*"))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private Set<String> getStopWords() {
        try {
            return parseStopWordsFromFile();
        } catch (IOException e) {
            throw new RuntimeException("Error reading stop words file", e);
        }
    }

    private Set<String> parseStopWordsFromFile() throws IOException {
        Path path = Path.of(WordFilterService.STOP_WORDS_FILE_PATH);
        try (var lines = Files.lines(path)) {
            return lines.map(String::trim)
                    .collect(Collectors.toSet());
        }
    }
}
