package com.btg.pactual.fundsapi.repository;

import com.btg.pactual.fundsapi.model.Client;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

@Repository
public class ClientRepository {

    private final DynamoDbTable<Client> clientTable;

    public ClientRepository(DynamoDbEnhancedClient enhancedClient) {

        this.clientTable = enhancedClient.table("Clients", TableSchema.fromBean(Client.class));
    }

    public Optional<Client> findById(String clientId) {
        Key key = Key.builder().partitionValue(clientId).build();
        return Optional.ofNullable(clientTable.getItem(key));
    }

    public Client save(Client client) {
        clientTable.putItem(client);
        return client;
    }

    public boolean existsById(String clientId) {
        return findById(clientId).isPresent();
    }
}