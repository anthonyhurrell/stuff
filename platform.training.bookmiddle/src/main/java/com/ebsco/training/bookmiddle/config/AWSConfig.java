package com.ebsco.training.bookmiddle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@Configuration
@Profile("devqa")
public class AWSConfig {

    @Bean
    public AmazonDynamoDB getAmazonDynamoDB() {
        return AmazonDynamoDBClient.builder()
                // for use with local profile
//                .withCredentials(new ProfileCredentialsProvider("eis-deliverydevqa"))
                .withRegion(Regions.US_EAST_1).build();
    }
}
