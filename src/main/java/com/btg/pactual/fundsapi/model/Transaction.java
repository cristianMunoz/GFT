package com.btg.pactual.fundsapi.model;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.math.BigDecimal;
import java.time.Instant;

@DynamoDbBean
@Getter
@Setter
public class Transaction {

    private String clientId;
    private String transactionId;
    private String fundId;
    private String fundName;
    private String type;
    private BigDecimal amount;
    private Instant timestamp;

    @DynamoDbPartitionKey
    public String getClientId() {
        return clientId;
    }

    @DynamoDbSortKey
    public String getTransactionId() {
        return transactionId;
    }
}