package com.wordcloud.worker.service;

import com.wordcloud.worker.dto.UploadDto;
import com.wordcloud.worker.model.Word;
import com.wordcloud.worker.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class WordCountService {
    private final WordRepository wordRepository;

    public WordCountService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public Map<String, Integer> GetResult(UploadDto uploadDto) {
        UUID userToken = UUID.fromString(uploadDto.getUserToken());
        List<String> words = SplitFileContentToWords(uploadDto.getUserFile());
        return CountWordOccurrences(words);
    }

    List<String> SplitFileContentToWords(byte[] fileContent) {
        String content = new String(fileContent, StandardCharsets.UTF_8);

        content = content.replaceAll("\\p{Punct}", "");
        String[] words = content.split("\\s+");

        return new ArrayList<>(Arrays.asList(words));
    }

    Map<String, Integer> CountWordOccurrences(List<String> words) {
        Map<String, Integer> wordCounts = new HashMap<>();

        for (String word : words) {
            word = word.toLowerCase().trim();

            if (!word.isEmpty()) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }

        return wordCounts;
    }
}
