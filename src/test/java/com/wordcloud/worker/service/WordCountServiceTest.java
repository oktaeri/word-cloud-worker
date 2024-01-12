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
        Map<String, Integer> result = getWordCountMap("short_text.txt");

        assertEquals(1, result.get("wordcountservice"));
        assertEquals(2, result.get("words"));
        assertEquals(2, result.get("this"));
        assertEquals(3, result.get("file"));
    }

    @Test
    public void testWordCountWithSpecialCharacters() throws IOException {
        Map<String, Integer> result = getWordCountMap("text_with_special_characters.txt");

        assertEquals(2, result.get("kärbes"));
        assertEquals(5, result.get("tööd"));
        assertEquals(2, result.get("leiutajateküla"));
    }

    @Test
    public void testWordCountWithDifferentCaseWords() throws IOException {
        Map<String, Integer> result = getWordCountMap("text_with_different_cases.txt");

        assertEquals(5, result.get("lorem"));
        assertEquals(2, result.get("ipsum"));
    }

    private byte[] readTestFile(String fileName) throws IOException {
        Path filePath = Paths.get("src/test/resources", fileName);
        return Files.readAllBytes(filePath);
    }

    private Map<String, Integer> getWordCountMap(String fileName) throws IOException {
        byte[] fileContent = readTestFile(fileName);
        List<String> wordsInFile = wordCountService.SplitFileContentToWords(fileContent);

        return wordCountService.CountWordOccurrences(wordsInFile);
    }
}
