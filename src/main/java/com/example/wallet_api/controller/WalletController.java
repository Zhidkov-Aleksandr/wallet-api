package com.example.wallet_api.controller;

import com.example.wallet_api.dto.WalletBalanceResponse;
import com.example.wallet_api.dto.WalletOperationRequest;
import com.example.wallet_api.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // Обработка пополнения и списания
    @PostMapping("/wallet")
    public ResponseEntity<Void> processOperation(
            @Valid @RequestBody WalletOperationRequest request) {

        walletService.processOperation(request);
        return ResponseEntity.ok().build();
    }

    // Получение баланса кошелька
    @GetMapping("/wallets/{walletId}")
    public WalletBalanceResponse getBalance(
            @PathVariable UUID walletId) {

        return walletService.getBalance(walletId);
    }
}