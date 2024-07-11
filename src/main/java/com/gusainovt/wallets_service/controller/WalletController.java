package com.gusainovt.wallets_service.controller;

import com.gusainovt.wallets_service.model.Wallet;
import com.gusainovt.wallets_service.service.WalletService;

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

    @PostMapping("/wallet")
    public Wallet createWallet(@RequestBody Wallet wallet) {
        return walletService.createWallet(wallet);
    }

    @PutMapping("/wallet/{WALLET_UUID}")
    public ResponseEntity<Wallet> editWallet(@PathVariable("WALLET_UUID") UUID uuid,
                                             @RequestBody Wallet wallet) {
        Wallet wallet1 = walletService.editWallet(uuid, wallet);
        return ResponseEntity.ok(wallet1);
    }

    @PutMapping("/wallet/transfer/{WALLET_UUID}")
    public ResponseEntity<Wallet> transferWallet(@PathVariable("WALLET_UUID") UUID uuid,
                                                 @RequestParam Long amount) {
        Wallet wallet1 = walletService.transferAmount(uuid, amount);
        return ResponseEntity.ok(wallet1);
    }

    @PutMapping("/wallet/withdrawal/{WALLET_UUID}")
    public ResponseEntity<Wallet> withdrawalWallet(@PathVariable("WALLET_UUID") UUID uuid,
                                                 @RequestParam Long amount) {
        Wallet wallet1 = walletService.withdrawalAmount(uuid, amount);
        return ResponseEntity.ok(wallet1);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public ResponseEntity<Wallet> getWallet(@PathVariable("WALLET_UUID") UUID uuid) {
        Wallet wallet = walletService.findWallet(uuid);
        return ResponseEntity.ok(wallet);
    }



}

