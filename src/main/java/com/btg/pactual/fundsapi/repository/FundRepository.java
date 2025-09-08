package com.btg.pactual.fundsapi.repository;

import com.btg.pactual.fundsapi.model.Fund;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

@Repository
public class FundRepository {

    private final DynamoDbTable<Fund> fundTable;

    public FundRepository(DynamoDbEnhancedClient enhancedClient) {
        this.fundTable = enhancedClient.table("Funds", TableSchema.fromBean(Fund.class));
    }

    public Optional<Fund> findById(String fundId) {
        Key key = Key.builder().partitionValue(fundId).build();
        return Optional.ofNullable(fundTable.getItem(key));
    }
}