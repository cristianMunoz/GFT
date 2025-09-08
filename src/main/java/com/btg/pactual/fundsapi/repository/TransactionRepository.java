package com.btg.pactual.fundsapi.repository;

import com.btg.pactual.fundsapi.model.Transaction;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    private final DynamoDbTable<Transaction> transactionTable;

    public TransactionRepository(DynamoDbEnhancedClient enhancedClient) {
        this.transactionTable = enhancedClient.table("Transactions", TableSchema.fromBean(Transaction.class));
    }

    public Transaction save(Transaction transaction) {
        transactionTable.putItem(transaction);
        return transaction;
    }

    public List<Transaction> findAllByClientId(String clientId) {
        
        QueryConditional queryConditional = QueryConditional.keyEqualTo(k -> k.partitionValue(clientId));

        return transactionTable.query(queryConditional).items().stream().collect(Collectors.toList());
    }
}