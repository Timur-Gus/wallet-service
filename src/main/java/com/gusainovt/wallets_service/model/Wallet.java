package com.gusainovt.wallets_service.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id")
    private UUID walletId;

    public enum OperationType {DEPOSIT, WITHDRAW}

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;
    @Column(name = "amount")
    private Long amount;


    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletId=" + walletId +
                ", operationType=" + operationType +
                ", amount=" + amount +
                '}';
    }
}

