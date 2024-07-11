package com.gusainovt.wallets_service.constants;

import com.gusainovt.wallets_service.model.Wallet;

import java.util.UUID;

import static com.gusainovt.wallets_service.model.Wallet.OperationType.*;

public class TestConstants {
    public static final String WALLET_ID_1 = "4c8545c3-4382-419a-8517-928c63712223";
    public static final Wallet WALLET_1 = new Wallet(
            UUID.fromString(WALLET_ID_1),
            WITHDRAW,
            5000L);
    public static final Wallet WALLET_2 = new Wallet(
            UUID.fromString(WALLET_ID_1),
            DEPOSIT,
            10000L);

    public static final Wallet WALLET_TRANSFER = new Wallet(
            UUID.fromString(WALLET_ID_1),
            WITHDRAW,
            7500L);
    public static final Wallet WALLET_WITHDRAWAL = new Wallet(
            UUID.fromString(WALLET_ID_1),
            WITHDRAW,
            2000L);

    public static final String PATH_WALLET = "/api/v1/wallet";
    public static final String PATH_TRANSFER = "/api/v1/wallet/transfer/";
    public static final String PATH_WITHDRAWAL = "/api/v1/wallet/withdrawal/";
    public static final String PATH_GET = "/api/v1/wallets/";
}
