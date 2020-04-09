package com.injian.kafka;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/4/9 16:26
 */
@Component
public class KafkaProducer {

    private static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    //发送消息方法
    public void send() {
        for(int i = 0; i < 5;i ++){
            Message message = new Message();
            message.setId(String.valueOf(System.currentTimeMillis()));
            message.setMsg(UUID.randomUUID().toString()+ "---" +i);
            logger.info("发送消息 ----->>>>>  message = {}", gson.toJson(message));
            kafkaTemplate.send("hello", gson.toJson(message));
        }
    }


}


