package com.asura.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

/**
 * @author zzyx 2021/1/2/002
 */
//@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.my-topic}")
    String myTopic;

    @Value("${kafka.topic.my-topic2}")
    String myTopic2;

    @Value("${kafka.topic.my-topic3}")
    String myTopic3;




    /**
     * JSON消息转换器
     */
    @Bean
    public RecordMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }

    /**
     * 通过注入一个NewTopic类型的Bean来创建topic，如果topic已存在，则会忽略。
     */
    @Bean
    public NewTopic myTopic(){
        return new NewTopic(myTopic,2,(short)1);
    }

    @Bean
    public NewTopic myTopic2(){
        return new NewTopic(myTopic2,1,(short)1);
    }

    @Bean
    public NewTopic myTopic3(){
        return new NewTopic(myTopic2,1,(short)1);
    }

}
