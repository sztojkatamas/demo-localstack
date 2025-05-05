package com.demo.fakeaws.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import java.net.URI;

@Configuration
public class AwsConfig {

    @Value("${localstack.endpoint:http://localhost:4566}")
    private String localstackEndpoint;

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    @Value("${aws.accessKeyId:dummy}")
    private String accessKeyId;

    @Value("${aws.secretKey:dummy}")
    private String secretKey;

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .endpointOverride(URI.create(localstackEndpoint))
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey)))
                .build();
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(URI.create(localstackEndpoint))
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey)))
                .build();
    }
}


