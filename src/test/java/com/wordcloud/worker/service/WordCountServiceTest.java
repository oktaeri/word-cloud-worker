package com.wordcloud.worker.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordCountServiceTest {
    private WordCountService wordCountService;

    @BeforeEach
    public void InitializeService() {
        wordCountService = new WordCountService(null);
    }

    @Test
    public void testWordCountWithListOfString() {
        List<String> words = List.of("Lorem", "ipsum", "dolor", "lorem", "dolor", "lorem", "lorem");
        Map<String, Integer> wordCounts = wordCountService.CountWordOccurrences(words);

        assertEquals(4, wordCounts.get("lorem"));
        assertEquals(2, wordCounts.get("dolor"));
        assertEquals(1, wordCounts.get("ipsum"));
    }

    @Test
    public void testWordCountWithShortTextFile() throws IOException {
        byte[] fileContent = readTestFile("short_text.txt");
        List<String> wordsInFile = wordCountService.SplitFileContentToWords(fileContent);

        Map<String, Integer> result = wordCountService.CountWordOccurrences(wordsInFile);

        assertEquals(1, result.get("wordcountservice"));
        assertEquals(2, result.get("words"));
        assertEquals(2, result.get("this"));
        assertEquals(3, result.get("file"));
    }

    private byte[] readTestFile(String fileName) throws IOException {
        Path filePath = Paths.get("src/test/resources", fileName);
        return Files.readAllBytes(filePath);
    }
}
