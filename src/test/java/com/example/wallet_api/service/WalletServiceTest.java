package com.example.wallet_api.service;

import com.example.wallet_api.dto.OperationType;
import com.example.wallet_api.dto.WalletOperationRequest;
import com.example.wallet_api.exception.InsufficientFundsException;
import com.example.wallet_api.model.Wallet;
import com.example.wallet_api.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    // тест: нельзя списать больше, чем есть
    @Test
    void withdrawShouldFailWhenNotEnoughMoney() {

        UUID walletId = UUID.randomUUID();
        walletRepository.save(new Wallet(walletId, 100));

        WalletOperationRequest request = new WalletOperationRequest();
        request.setWalletId(walletId);
        request.setOperationType(OperationType.WITHDRAW);
        request.setAmount(200);


        assertThrows(InsufficientFundsException.class, () ->
                walletService.processOperation(request)
        );
    }
}