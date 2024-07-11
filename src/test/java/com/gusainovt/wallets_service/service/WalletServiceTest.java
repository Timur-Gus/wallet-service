package com.gusainovt.wallets_service.service;

import com.gusainovt.wallets_service.exception.InsufficientFundsException;
import com.gusainovt.wallets_service.model.Wallet;
import com.gusainovt.wallets_service.repositories.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.gusainovt.wallets_service.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    @InjectMocks
    WalletService walletService;
    @Mock
    WalletRepository walletRepository;


    @BeforeEach
    void setUp() {
    }
    @Test
    void createWallet() {
        when(walletRepository.save(WALLET_1))
                .thenReturn(WALLET_1);
        Wallet actual = walletService.createWallet(WALLET_1);
        assertEquals(WALLET_1, actual);
    }

    @Test
    void editWallet() {
        when(walletRepository.findById(UUID.fromString(WALLET_ID_1)))
                .thenReturn(Optional.of(WALLET_1));
        when(walletRepository.save(any(Wallet.class)))
                .thenReturn(WALLET_2);
        Wallet actual = walletService.editWallet(UUID.fromString(WALLET_ID_1), WALLET_2);
        assertEquals(WALLET_2.getAmount(),actual.getAmount());
        assertEquals(WALLET_2.getOperationType(), actual.getOperationType());
    }

    @Test
    void findWallet() {
        when(walletRepository.findById(UUID.fromString(WALLET_ID_1)))
                .thenReturn(Optional.of(WALLET_1));
        Wallet actual = walletService.findWallet(UUID.fromString(WALLET_ID_1));
        assertEquals(WALLET_1, actual);
    }

    @Test
    void transferAmount() {
        when(walletRepository.findById(UUID.fromString(WALLET_ID_1)))
                .thenReturn(Optional.of(WALLET_1));
        when(walletRepository.save(any(Wallet.class)))
                .thenReturn(WALLET_TRANSFER);
        Wallet actual = walletService.transferAmount(UUID.fromString(WALLET_ID_1), 2500L);
        assertEquals(WALLET_TRANSFER.getAmount(),actual.getAmount());
    }

    @Test
    void withdrawalAmount() {
        when(walletRepository.findById(UUID.fromString(WALLET_ID_1)))
                .thenReturn(Optional.of(WALLET_1));
        when(walletRepository.save(any(Wallet.class)))
                .thenReturn(WALLET_WITHDRAWAL);
        Wallet actual = walletService.withdrawalAmount(UUID.fromString(WALLET_ID_1), 3000L);
        assertEquals(WALLET_WITHDRAWAL.getAmount(),actual.getAmount());
    }

    @Test
    void withdrawalAmountNegative() {
        when(walletRepository.findById(UUID.fromString(WALLET_ID_1)))
                .thenReturn(Optional.of(WALLET_1));
        assertThrows(InsufficientFundsException.class, () -> {
            walletService.withdrawalAmount(UUID.fromString(WALLET_ID_1), 10000L);
        });
    }

}