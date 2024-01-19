package com.wordcloud.worker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordFilterServiceTest {
    private WordFilterService wordFilterService;

    @BeforeEach
    public void initializeService() {
        wordFilterService = new WordFilterService();
    }

    @Test
    void givenListOfWords_whenFilteringWordsAndExcludingCommon_thenShouldNotContainCommonWords(){
        List<String> originalWords = new ArrayList<>(List.of("the", "quick", "on", "fox", "jumped", "at", "the", "lazy", "fox"));
        List<String> filteredWords = wordFilterService.filter(originalWords, true, "");

        assertFalse(filteredWords.contains("the"));
        assertFalse(filteredWords.contains("on"));
        assertFalse(filteredWords.contains("at"));
    }

    @Test
    void givenListOfWords_whenFilteringWordsAndExcludingCommon_thenShouldNotRemoveUncommonWords(){
        List<String> originalWords = new ArrayList<>(List.of("the", "quick", "on", "fox", "jumped", "at", "the", "lazy", "fox"));
        List<String> filteredWords = wordFilterService.filter(originalWords, true, "");

        assertTrue(filteredWords.contains("quick"));
        assertTrue(filteredWords.contains("fox"));
    }

    @Test
    void whenFilteringWordsAndExcludingOneCustomWord_thenShouldNotContainCustomWord() {
        List<String> originalWords = new ArrayList<>(List.of("red", "quick",
                "on", "fox", "jumped", "at", "the", "lazy", "fox",
                "garbage", "fox", "dog"));
        List<String> filteredWords = wordFilterService.filter(originalWords, false, "fox");

        assertFalse(filteredWords.contains("fox"));
    }

    @Test
    void whenFilteringWordsAndExcludingMultipleCustomWords_thenShouldNotContainCustomWords() {
        List<String> originalWords = new ArrayList<>(List.of("red", "quick",
                "on", "fox", "jumped", "at", "the", "lazy", "fox",
                "garbage", "fox", "dog"));
        List<String> filteredWords = wordFilterService.filter(originalWords, false, "red, quick");

        assertFalse(filteredWords.contains("red"));
        assertFalse(filteredWords.contains("quick"));
    }

    @Test
    void whenFilteringCustomWords_thenShouldNotRemoveOtherWords() {
        List<String> originalWords = new ArrayList<>(List.of("red", "quick",
                "on", "fox", "jumped", "at", "the", "lazy", "fox",
                "garbage", "fox", "dog"));
        List<String> filteredWords = wordFilterService.filter(originalWords, false, "red, quick");

        assertTrue(filteredWords.contains("dog"));
        assertTrue(filteredWords.contains("on"));
        assertTrue(filteredWords.contains("at"));
    }

    @Test
    void whenFilteringCommonAndCustomWords_thenShouldNotContainCommonAndCustomWords() {
        List<String> originalWords = new ArrayList<>(List.of("red", "quick",
                "on", "fox", "jumped", "at", "the", "lazy", "fox",
                "garbage", "fox", "dog"));
        List<String> filteredWords = wordFilterService.filter(originalWords, true, "garbage, quick");

        assertFalse(filteredWords.contains("garbage"));
        assertFalse(filteredWords.contains("quick"));
        assertFalse(filteredWords.contains("at"));
        assertFalse(filteredWords.contains("the"));
    }

    @Test
    void whenFilteringCommonAndCustomWords_thenShouldNotRemoveOtherWords() {
        List<String> originalWords = new ArrayList<>(List.of("red", "quick",
                "on", "fox", "jumped", "at", "the", "lazy", "fox",
                "garbage", "fox", "dog"));
        List<String> filteredWords = wordFilterService.filter(originalWords, true, "garbage, quick");

        assertTrue(filteredWords.contains("red"));
        assertTrue(filteredWords.contains("lazy"));
        assertTrue(filteredWords.contains("fox"));
    }

    @Test
    void whenFilteringNothing_thenShouldNotRemoveAnyWords() {
        List<String> originalWords = new ArrayList<>(List.of("red", "quick", "on"));
        List<String> filteredWords = wordFilterService.filter(originalWords, false, "");

        assertTrue(filteredWords.contains("red"));
        assertTrue(filteredWords.contains("quick"));
        assertTrue(filteredWords.contains("on"));
    }

    @Test
    void whenFilteringCustomWordsWithDifferentCases_thenShouldNotContainCustomWords() {
        List<String> originalWords = new ArrayList<>(List.of("red", "quick", "on", "tomato", "toast"));
        List<String> filteredWords = wordFilterService.filter(originalWords, false, "TOAST, tomATo");

        assertFalse(filteredWords.contains("toast"));
        assertFalse(filteredWords.contains("tomato"));
    }

    @Test
    void whenFilteringCustomWordsWithSpecialCharacters_thenShouldNotContainCustomWords() {
        List<String> originalWords = new ArrayList<>(List.of("red", "qüick", "ön", "tomato", "töäst"));
        List<String> filteredWords = wordFilterService.filter(originalWords, false, "töäst, ön");

        assertFalse(filteredWords.contains("töäst"));
        assertFalse(filteredWords.contains("ön"));
    }

    @Test
    void whenFilteringCustomWordsWithSpecialCharactersAndDifferentCases_thenShouldNotContainCustomWords() {
        List<String> originalWords = new ArrayList<>(List.of("red", "qüick", "ön", "tomato", "töäst"));
        List<String> filteredWords = wordFilterService.filter(originalWords, false, "tÖäST, öN");

        assertFalse(filteredWords.contains("töäst"));
        assertFalse(filteredWords.contains("ön"));
    }
}
