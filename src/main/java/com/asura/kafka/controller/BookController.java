package com.asura.kafka.controller;

import com.asura.kafka.entity.BookTest;
import com.asura.kafka.service.BookProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zzyx 2021/1/3/003
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Value("${kafka.topic.my-topic}")
    String myTopic;

    @Value("${kafka.topic.my-topic2}")
    String myTopic2;

    @Value("${kafka.topic.my-topic3}")
    String myTopic3;

    @Autowired
    private BookProducerService producer;

    private AtomicLong atomicLong = new AtomicLong();

    @PostMapping
    public void sendMessageToKafkaTopic(@RequestParam("name") String name) {
        System.out.println(name);
        this.producer.sendMessage(myTopic, new BookTest(atomicLong.addAndGet(1), name));
        this.producer.sendMessage(myTopic2, new BookTest(atomicLong.addAndGet(1), name));
        this.producer.sendMessage(myTopic3, new BookTest(atomicLong.addAndGet(1), name));
    }

}
