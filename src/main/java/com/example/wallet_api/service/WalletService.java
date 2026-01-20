package com.example.wallet_api.service;

import com.example.wallet_api.dto.OperationType;
import com.example.wallet_api.dto.WalletBalanceResponse;
import com.example.wallet_api.dto.WalletOperationRequest;
import com.example.wallet_api.exception.InsufficientFundsException;
import com.example.wallet_api.exception.WalletNotFoundException;
import com.example.wallet_api.model.Wallet;
import com.example.wallet_api.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void processOperation(WalletOperationRequest request) {

        // Ищем кошелёк по id
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(WalletNotFoundException::new);

        // В зависимости от типа операции меняем баланс
        if (request.getOperationType() == OperationType.WITHDRAW) {

            // Проверяем, хватает ли денег
            if (wallet.getBalance() < request.getAmount()) {
                throw new InsufficientFundsException();
            }

            wallet.setBalance(wallet.getBalance() - request.getAmount());

        } else {
            wallet.setBalance(wallet.getBalance() + request.getAmount());
        }

        // Сохраняем изменения
        walletRepository.save(wallet);
    }

    @Transactional
    public WalletBalanceResponse getBalance(UUID walletId) {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(WalletNotFoundException::new);

        return new WalletBalanceResponse(wallet.getId(), wallet.getBalance());
    }
}