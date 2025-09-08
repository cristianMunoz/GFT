package com.btg.pactual.fundsapi.controller;

import com.btg.pactual.fundsapi.dto.SubscriptionRequestDTO;
import com.btg.pactual.fundsapi.dto.TransactionDTO;
import com.btg.pactual.fundsapi.service.FundService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients/{clientId}")
public class FundController {

    private final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<TransactionDTO> subscribe(
            @PathVariable String clientId,
            @Valid @RequestBody SubscriptionRequestDTO request) {

        TransactionDTO transaction = fundService.subscribeToFund(clientId, request.getFundId());
        
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(@PathVariable String clientId) {
        List<TransactionDTO> history = fundService.getTransactionHistory(clientId);
        
        return ResponseEntity.ok(history);
    }

    @DeleteMapping("/subscriptions/{fundId}")
    public ResponseEntity<TransactionDTO> cancelSubscription(
            @PathVariable String clientId,
            @PathVariable String fundId) {
        
        TransactionDTO transaction = fundService.cancelSubscription(clientId, fundId);
        
        return ResponseEntity.ok(transaction);
    }
}