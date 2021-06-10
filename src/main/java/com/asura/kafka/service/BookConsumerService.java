package com.asura.kafka.service;

import com.asura.kafka.entity.BookTest;
import com.asura.kafka.event.BookEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zzyx 2021/1/3/003
 */
@Service
public class BookConsumerService {

    @Value("${kafka.topic.my-topic}")
    private String myTopic;

    @Value("${kafka.topic.my-topic2}")
    private String myTopic2;

    @Resource
    private ApplicationEventPublisher eventPublisher;

    private final static Logger LOGGER = LoggerFactory.getLogger(BookConsumerService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${kafka.topic.my-topic}", groupId = "group1")
    public void consumeMessage(ConsumerRecord<String, String> bookConsumerRecord) {
        try {
            BookTest bookTest = objectMapper.readValue(bookConsumerRecord.value(), BookTest.class);
            eventPublisher.publishEvent(new BookEvent(this, bookTest));
            LOGGER.info("消费者消费topic:{} partition:{}的消息 -> {}", bookConsumerRecord.topic(), bookConsumerRecord.partition(), bookTest.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @KafkaListener(topics = "${kafka.topic.my-topic2}", groupId = "group2")
    public void consumeMessage2(BookTest bookTest) {
        LOGGER.info("消费者消费{}的消息->{}", myTopic2, bookTest.toString());
    }


    /*@KafkaListener(topics = "${kafka.topic.my-topic3}",groupId = "cetcocean_data_fusion")
    public void consumeMessage3(ConsumerRecord<String, String> record) {
        LOGGER.info("消费者消费topic:{} partition:{}的消息 -> {}", record.topic(),
                record.partition(), record);
    }*/
}
