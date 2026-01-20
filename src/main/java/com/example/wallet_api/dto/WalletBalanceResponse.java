package com.example.wallet_api.dto;

import java.util.UUID;

public class WalletBalanceResponse {

    private UUID walletId;
    private long balance;

    public WalletBalanceResponse(UUID walletId, long balance) {
        this.walletId = walletId;
        this.balance = balance;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public long getBalance() {
        return balance;
    }
}