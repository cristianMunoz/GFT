package com.btg.pactual.fundsapi.model;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;

@DynamoDbBean
@Getter
@Setter
public class Fund {

    private String fundId;
    private String name;
    private BigDecimal minAmount;
    private String category;

    @DynamoDbPartitionKey
    public String getFundId() {
        return fundId;
    }
}