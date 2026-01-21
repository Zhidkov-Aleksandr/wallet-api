package com.example.wallet_api.controller;

import com.example.wallet_api.model.Wallet;
import com.example.wallet_api.repository.WalletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // тест: успешное пополнение
    @Test
    void shouldDepositMoney() throws Exception {

        UUID walletId = UUID.randomUUID();
        walletRepository.save(new Wallet(walletId, 100));

        String json = """
                {
                  "walletId": "%s",
                  "operationType": "DEPOSIT",
                  "amount": 50
                }
                """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(150));
    }

    // тест: кошелёк не найден
    @Test
    void shouldReturn404WhenWalletNotFound() throws Exception {

        mockMvc.perform(get("/api/v1/wallets/" + UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("WALLET_NOT_FOUND"));
    }

    // тест: недостаточно средств
    @Test
    void shouldReturnBadRequestWhenInsufficientFunds() throws Exception {

        UUID walletId = UUID.randomUUID();
        walletRepository.save(new Wallet(walletId, 100));

        String json = """
                {
                  "walletId": "%s",
                  "operationType": "WITHDRAW",
                  "amount": 500
                }
                """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("INSUFFICIENT_FUNDS"));
    }

    // 5️⃣ тест: "кривой" JSON
    @Test
    void shouldReturnBadRequestForInvalidJson() throws Exception {

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("INVALID_JSON"));
    }
}