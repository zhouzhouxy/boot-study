package com.asura.kafka.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author zzyx 2021/1/2/002
 */
@Service
public class BookProducerService {

    private static final Logger logger = LoggerFactory.getLogger(BookProducerService.class);

    @Qualifier(value = "kafkaTemplate")
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Object o) {
        try {
           /* 属于同步的发送方式不推荐，没哟ul用到`Future`对象的特性
         kafkaTemplate调用send()方法实际上返回的是ListenableFuture对象。
          SendResult<String, Object> sendResult = kafkaTemplate.send(topic, o).get();
            if(sendResult.getRecordMetadata()!=null){
                logger.info("生产者成功发送消息到："+sendResult.getProducerRecord().topic()+"->"+sendResult.getProducerRecord().value().toString());
            }*/
            //优化方法1
           /*  ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);

            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    logger.info("生产者发送消息:{} 失败，原因：{}",o.toString(),throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, Object> sendResult) {
                    logger.info("生产者成功发送消息到"+topic+"->"+sendResult.getProducerRecord().value().toString());
                }
            });*/
            //优化方式2 用lambda表达式再继续优化
           /* ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topic, o);
            send.addCallback(result-> {
                        assert result != null;
                        logger.info("生产者成功发送消息到topic:{},partition:{}的消息",result.getRecordMetadata().topic(),result.getRecordMetadata().partition());
                    },
                    ex->logger.error("生产者发送消息失败，原因：{}",ex.getMessage()));*/
            //优化方式3，如果我们想在发送的时候带上timestamp(时间戳)、key等消息的话，sendMessage()可以这样子写
            ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, null, System.currentTimeMillis(), String.valueOf(o.hashCode()), o);
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(producerRecord);
            future.addCallback(result -> {
                        assert result != null;
                        logger.info("生产者成功发送消息到topic:{},partition:{}的消息", result.getRecordMetadata().topic(), result.getRecordMetadata().partition());
                    },
                    ex -> logger.error("生产者发送消息失败，原因：{}", ex.getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
