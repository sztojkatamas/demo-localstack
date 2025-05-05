package com.demo.fakeaws.controller;

import com.demo.fakeaws.aws.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EchoController {

    @Autowired
    NotificationService notify;

    @GetMapping("/echo/{text}")
    public String echo(@PathVariable String text) {
        String topicARN  = notify.getOrCreateTopicArn("myTopic");
        notify.publishToSns(topicARN, text);
        log.info("--------------------------------------");

        String queue = notify.getOrCreateQueueUrl("myQueue");
        notify.sendToSqs(queue,text);
        return text;
    }
}