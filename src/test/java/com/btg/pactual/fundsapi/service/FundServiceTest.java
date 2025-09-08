package com.btg.pactual.fundsapi.service;

import com.btg.pactual.fundsapi.exception.InsufficientBalanceException;
import com.btg.pactual.fundsapi.model.Client;
import com.btg.pactual.fundsapi.model.Fund;
import com.btg.pactual.fundsapi.model.Transaction;
import com.btg.pactual.fundsapi.repository.ClientRepository;
import com.btg.pactual.fundsapi.repository.FundRepository;
import com.btg.pactual.fundsapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FundServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private FundRepository fundRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FundService fundService;

    @Test
    void subscribeToFund_cuandoSaldoEsSuficiente_debeSerExitoso() {
        
        Client mockClient = new Client();
        mockClient.setBalance(new BigDecimal("100000"));
        mockClient.setNotificationPreference("EMAIL");

        Fund mockFund = new Fund();
        mockFund.setMinAmount(new BigDecimal("50000"));
        mockFund.setName("Mi Fondo");

        when(clientRepository.findById("clientId-123")).thenReturn(Optional.of(mockClient));
        when(fundRepository.findById("fundId-abc")).thenReturn(Optional.of(mockFund));

        fundService.subscribeToFund("clientId-123", "fundId-abc");

        verify(clientRepository, times(1)).save(any(Client.class));
        
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void subscribeToFund_cuandoSaldoEsInsuficiente_debeLanzarExcepcion() {
        
        Client mockClient = new Client();
        mockClient.setBalance(new BigDecimal("40000"));
        Fund mockFund = new Fund();
        mockFund.setMinAmount(new BigDecimal("50000"));
        mockFund.setName("Mi Fondo Caro");

        when(clientRepository.findById("clientId-123")).thenReturn(Optional.of(mockClient));
        when(fundRepository.findById("fundId-abc")).thenReturn(Optional.of(mockFund));

        assertThrows(InsufficientBalanceException.class, () -> {
            fundService.subscribeToFund("clientId-123", "fundId-abc");
        });

        verify(clientRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }
}