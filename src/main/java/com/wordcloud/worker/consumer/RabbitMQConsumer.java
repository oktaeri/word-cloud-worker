package com.wordcloud.worker.consumer;

import com.wordcloud.worker.dto.UploadDto;
import com.wordcloud.worker.model.Word;
import com.wordcloud.worker.repository.WordRepository;
import com.wordcloud.worker.service.WordCountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final WordCountService wordCountService;

    public RabbitMQConsumer(WordCountService wordCountService) {
        this.wordCountService = wordCountService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(UploadDto uploadDto) {
        LOGGER.info(String.format("Received message -> Token: %s MinCount: %s",
                uploadDto.getUserToken(),
                uploadDto.getMinimumCount()));
        wordCountService.saveWordCountsAsync(uploadDto);
    }
}
