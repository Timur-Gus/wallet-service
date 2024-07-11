package com.gusainovt.wallets_service.service;

import com.gusainovt.wallets_service.exception.InsufficientFundsException;
import com.gusainovt.wallets_service.exception.NotFoundWalletException;
import com.gusainovt.wallets_service.model.Wallet;
import com.gusainovt.wallets_service.repositories.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The service of interaction with the wallet repository
 */
@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Creating a new wallet
     * @param wallet new object {@link Wallet}
     * @return new object {@link Wallet}
     */
    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    /**
     * Changing the existing wallet
     * @param uuid The wallet id in the uuid format
     * @param wallet The object {@link Wallet} with changed parameters
     * @return The modified object {@link Wallet}
     */
    public Wallet editWallet(UUID uuid, Wallet wallet) {
        Wallet exsitingWallet = findWallet(uuid);
        exsitingWallet.setOperationType(wallet.getOperationType());
        exsitingWallet.setAmount(wallet.getAmount());
        return walletRepository.save(exsitingWallet);
    }

    /**
     * Searching the wallet by wallet id
     * @param uuid The wallet id in the uuid format
     * @return The object {@link Wallet}
     */
    public Wallet findWallet(UUID uuid) {
        return walletRepository.findById(uuid).orElseThrow(NotFoundWalletException::new);
    }

    /**
     * Transferring funds to a wallet account
     * @param uuid The wallet id in the uuid format
     * @param amount The numeric value to be added
     * @return The object {@link Wallet}
     */
    public Wallet transferAmount(UUID uuid, Long amount) {
        Wallet exsitingWallet = findWallet(uuid);
        Long newAmount = exsitingWallet.getAmount() + amount;
        exsitingWallet.setAmount(newAmount);
        return walletRepository.save(exsitingWallet);
    }

    /**
     * Withdrawal of funds from the wallet
     * @param uuid The wallet id in the uuid format
     * @param amount The numeric value that needs to be decreased
     * @return The object {@link Wallet}
     */
    public Wallet withdrawalAmount(UUID uuid, Long amount) {
        Wallet exsitingWallet = findWallet(uuid);
        if (amount <= exsitingWallet.getAmount()) {
            Long newAmount = exsitingWallet.getAmount() - amount;
            exsitingWallet.setAmount(newAmount);
            return walletRepository.save(exsitingWallet);
        }
        else {
            throw new InsufficientFundsException();
        }
    }

}
