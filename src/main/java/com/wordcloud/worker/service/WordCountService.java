package com.wordcloud.worker.service;

import com.wordcloud.worker.dto.UploadDto;
import com.wordcloud.worker.model.UserToken;
import com.wordcloud.worker.model.WordCount;
import com.wordcloud.worker.repository.UserTokenRepository;
import com.wordcloud.worker.repository.WordCountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordCountService {
    private final WordCountRepository wordCountRepository;
    private final UserTokenRepository userTokenRepository;
    private final WordFilterService wordFilterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WordCountService.class);

    public WordCountService(
            WordCountRepository wordCountRepository,
            UserTokenRepository userTokenRepository, WordFilterService wordFilterService) {
        this.wordCountRepository = wordCountRepository;
        this.userTokenRepository = userTokenRepository;
        this.wordFilterService = wordFilterService;
    }

    @Async
    public void saveWordCountsAsync(UploadDto uploadDto) {
        String userTokenText = uploadDto.getUserToken();
        UserToken userToken = userTokenRepository.findByToken(userTokenText);

        List<WordCount> wordCounts = getWordCounts(uploadDto, userToken);

        userToken.setProcessing(false);
        userTokenRepository.save(userToken);

        wordCountRepository.saveAll(wordCounts);
        LOGGER.info(String.format("Saving completed for user -> %s", userTokenText));
    }

    private List<WordCount> getWordCounts(UploadDto uploadDto, UserToken userToken) {
        List<String> words = splitFileContentToWords(uploadDto.getUserFile());
        words = wordFilterService.filter(words, uploadDto.isFilterCommonWords(), uploadDto.getFilterCustomWords());
        Map<String, Integer> wordOccurrences = countWordOccurrences(words, uploadDto.getMinimumCount());

        return wordOccurrences.entrySet().stream()
                .map(entry -> createWordCount(entry.getKey(), entry.getValue(), userToken))
                .collect(Collectors.toList());
    }

    protected List<String> splitFileContentToWords(byte[] fileContent) {
        String content = new String(fileContent, StandardCharsets.UTF_8);

        content = content.replaceAll("\\p{Punct}", "");
        String[] words = content.split("\\s+");

        return new ArrayList<>(Arrays.asList(words));
    }

    protected Map<String, Integer> countWordOccurrences(List<String> words) {
        return countWordOccurrences(words, null);
    }

    protected Map<String, Integer> countWordOccurrences(List<String> words, Integer minimumCount) {
        Map<String, Integer> wordCounts = new HashMap<>();

        for (String word : words) {
            word = word.toLowerCase().trim();

            if (!word.isEmpty()) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }

        if (minimumCount != null && minimumCount > 0) {
            wordCounts.entrySet().removeIf(entry -> entry.getValue() < minimumCount);
        }

        return wordCounts;
    }

    private WordCount createWordCount(String wordText, Integer count, UserToken userToken) {
        WordCount wordCount = new WordCount();
        wordCount.setCount(count);
        wordCount.setUserToken(userToken);
        wordCount.setWord(wordText);

        return wordCount;
    }
}
