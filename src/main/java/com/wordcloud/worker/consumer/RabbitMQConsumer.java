package com.wordcloud.worker.consumer;

import com.wordcloud.worker.dto.TestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(TestDto testDto) {
        LOGGER.info(String.format("Received message -> %s", testDto));
    }
}
