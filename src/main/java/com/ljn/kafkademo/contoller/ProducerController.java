package com.ljn.kafkademo.contoller;

import com.google.gson.Gson;
import com.ljn.kafkademo.common.MessageEntity;
import com.ljn.kafkademo.common.Response;
import com.ljn.kafkademo.producer.SimpleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "producer")
public class ProducerController {

    @Value("${kafka.topic.default}")
    private String topic;

    private final static String KEY = "test";

    @Autowired
    private SimpleProducer simpleProducer;

    @PostMapping("hello")
    public String hello(@RequestBody MessageEntity entity) {
        return new Gson().toJson(entity);
    }

    @PostMapping("send")
    public String send(@RequestBody MessageEntity entity) {
        simpleProducer.send(topic, KEY, entity);
        return new Gson().toJson(new Response(200, "ok"));
    }
}
