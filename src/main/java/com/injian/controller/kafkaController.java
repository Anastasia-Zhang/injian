package com.injian.controller;

import com.injian.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Zhang Xinyu
 * @version 1.0
 * @date 2020/4/9 16:47
 */
@Controller
public class kafkaController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping("/senMsg")
    @ResponseBody
    public String testSendMsg(){
        kafkaProducer.send();
        return "success";
    }
}
