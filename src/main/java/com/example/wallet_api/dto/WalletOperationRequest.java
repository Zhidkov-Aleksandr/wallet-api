package com.example.wallet_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class WalletOperationRequest {

    @NotNull
    private UUID walletId;

    @NotNull
    private OperationType operationType;

    @Positive
    private long amount;

    public UUID getWalletId() {
        return walletId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public long getAmount() {
        return amount;
    }
}