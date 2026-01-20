package com.example.wallet_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    private UUID id;

    // Баланс храним в минимальных единицах (в копейках)
    @Column(nullable = false)
    private long balance;

    // Поле для optimistic locking
    @Version
    private long version;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Wallet() {        // пустой конструктор для JPA

    }

    public Wallet(UUID id, long balance) {
        this.id = id;
        this.balance = balance;
    }

    @PrePersist
    void onCreate() { // автоматически выставляет дату создания при первом сохранении
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {  // автоматически заполняет дату последнего обновления при каждом изменении сущности.
        updatedAt = LocalDateTime.now();
    }

    // геттеры и сеттеры для доступа к полям

    public UUID getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
