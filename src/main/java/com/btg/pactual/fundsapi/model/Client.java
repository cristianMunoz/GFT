package com.btg.pactual.fundsapi.model;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;

@DynamoDbBean
@Getter
@Setter
public class Client {

    private String clientId;
    private BigDecimal balance;
    private String email;
    private String phoneNumber;
    private String notificationPreference;

    @DynamoDbPartitionKey
    public String getClientId() {
        return clientId;
    }
}