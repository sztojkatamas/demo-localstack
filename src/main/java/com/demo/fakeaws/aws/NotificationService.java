package com.demo.fakeaws.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private SnsClient snsClient;

    @Autowired
    private SqsClient sqsClient;

    public String getOrCreateTopicArn(String topicName) {
        ListTopicsResponse listTopicsResponse = snsClient.listTopics();
        for (Topic topic : listTopicsResponse.topics()) {
            String arn = topic.topicArn();
            // Extract the topic name from the ARN
            String existingTopicName = arn.substring(arn.lastIndexOf(":") + 1);
            if (existingTopicName.equals(topicName)) {
                log.info("Topic '{}' already exists with ARN: {}", topicName, arn);
                return arn;
            }
        }

        // Topic does not exist, so create it
        CreateTopicRequest createTopicRequest = CreateTopicRequest.builder().name(topicName).build();
        CreateTopicResponse createTopicResponse = snsClient.createTopic(createTopicRequest);
        String newTopicArn = createTopicResponse.topicArn();
        log.info("Created topic '{}' with ARN: {}", topicName, newTopicArn);
        return newTopicArn;
    }

    public String getOrCreateQueueUrl(String queueName) {
        ListQueuesResponse listQueuesResponse = sqsClient.listQueues();
        List<String> queueUrls = listQueuesResponse.queueUrls();

        if (queueUrls != null) {
            for (String queueUrl : queueUrls) {
                // Extract the queue name from the URL
                String existingQueueName = queueUrl.substring(queueUrl.lastIndexOf("/") + 1);
                if (existingQueueName.equals(queueName)) {
                    log.info("Queue '{}' already exists with URL: {}", queueName, queueUrl);
                    return queueUrl;
                }
            }
        }

        // Queue does not exist, so create it
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder().queueName(queueName).build();
        CreateQueueResponse createQueueResponse = sqsClient.createQueue(createQueueRequest);
        String newQueueUrl = createQueueResponse.queueUrl();
        log.info("Created queue '{}' with URL: {}", queueName, newQueueUrl);
        return newQueueUrl;
    }
    public String publishToSns(String topicArn, String message) {
        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .build();
        PublishResponse response = snsClient.publish(publishRequest);
        log.info("Published message to SNS. Message ID: {}", response.messageId());
        return response.messageId();
    }

    public String sendToSqs(String queueUrl, String messageBody) {
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();
        SendMessageResponse response = sqsClient.sendMessage(sendMessageRequest);
        log.info("Sent message to SQS. Message ID: {}", response.messageId());
        return response.messageId();
    }
}
