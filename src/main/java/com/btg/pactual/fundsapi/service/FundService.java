package com.btg.pactual.fundsapi.service;

import com.btg.pactual.fundsapi.dto.TransactionDTO;
import com.btg.pactual.fundsapi.exception.InsufficientBalanceException;
import com.btg.pactual.fundsapi.exception.ResourceNotFoundException;
import com.btg.pactual.fundsapi.model.Client;
import com.btg.pactual.fundsapi.model.Fund;
import com.btg.pactual.fundsapi.model.Transaction;
import com.btg.pactual.fundsapi.repository.ClientRepository;
import com.btg.pactual.fundsapi.repository.FundRepository;
import com.btg.pactual.fundsapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FundService {

    private final ClientRepository clientRepository;
    private final FundRepository fundRepository;
    private final TransactionRepository transactionRepository;

    public FundService(ClientRepository clientRepository, FundRepository fundRepository, TransactionRepository transactionRepository) {
        this.clientRepository = clientRepository;
        this.fundRepository = fundRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionDTO subscribeToFund(String clientId, String fundId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + clientId + " no encontrado."));

        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new ResourceNotFoundException("Fondo con ID " + fundId + " no encontrado."));

        if (client.getBalance().compareTo(fund.getMinAmount()) < 0) {
            throw new InsufficientBalanceException("No tiene saldo disponible para vincularse al fondo " + fund.getName());
        }

        client.setBalance(client.getBalance().subtract(fund.getMinAmount()));
        clientRepository.save(client);

        Transaction transaction = new Transaction();
        transaction.setClientId(clientId);
        transaction.setTransactionId(Instant.now().toString() + "-" + UUID.randomUUID());
        transaction.setFundId(fund.getFundId());
        transaction.setFundName(fund.getName());
        transaction.setAmount(fund.getMinAmount());
        transaction.setType("SUBSCRIPTION");
        transaction.setTimestamp(Instant.now());
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        System.out.println("Enviando notificación a " + client.getNotificationPreference() + "...");

        return convertToDto(savedTransaction);
    }
    
    public List<TransactionDTO> getTransactionHistory(String clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Cliente con ID " + clientId + " no encontrado.");
        }
        
        return transactionRepository.findAllByClientId(clientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO cancelSubscription(String clientId, String fundId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente con ID " + clientId + " no encontrado."));
        
        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new ResourceNotFoundException("Fondo con ID " + fundId + " no encontrado."));


        client.setBalance(client.getBalance().add(fund.getMinAmount()));
        clientRepository.save(client);

        Transaction transaction = new Transaction();
        transaction.setClientId(clientId);
        transaction.setTransactionId(Instant.now().toString() + "-" + UUID.randomUUID());
        transaction.setFundId(fund.getFundId());
        transaction.setFundName(fund.getName());
        transaction.setAmount(fund.getMinAmount());
        transaction.setType("CANCELLATION");
        transaction.setTimestamp(Instant.now());
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        return convertToDto(savedTransaction);
    }

    private TransactionDTO convertToDto(Transaction transaction) {
        return new TransactionDTO(
                transaction.getTransactionId(),
                transaction.getFundName(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTimestamp()
        );
    }
}