package com.gusainovt.wallets_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusainovt.wallets_service.WalletsServiceApplication;
import com.gusainovt.wallets_service.model.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static com.gusainovt.wallets_service.constants.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WalletsServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerRestTemplateTest {

    @Autowired
    TestRestTemplate restTemplate;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void createWalletTest() {
        ResponseEntity<Wallet> response = createWalletResponse();
        Wallet body = response.getBody();
        checkWallet(body,WALLET_1.getOperationType(), WALLET_1.getAmount());
    }

    @Test
    void getWalletTest() {
        ResponseEntity<Wallet> response = getWalletResponse();
        Wallet body = response.getBody();
        checkWallet(body,WALLET_1.getOperationType(), WALLET_1.getAmount());
    }

    @Test
    void editWallet() {
        ResponseEntity<Wallet> response = createWalletResponse();
        Wallet body = response.getBody();
        body.setAmount(15000L);
        response = restTemplate.exchange(
                PATH_WALLET + "/" + WALLET_ID_1,
                HttpMethod.PUT,
                new HttpEntity<>(body),
                Wallet.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        response = getWalletResponse();
        body = response.getBody();
        checkWallet(body, WALLET_1.getOperationType(), 15000L);
    }

    @Test
    void transferWalletTest() {
        ResponseEntity<Wallet> response = createWalletResponse();
        Wallet body = response.getBody();
        body.setAmount(10000L);
        response = restTemplate.exchange(
                PATH_TRANSFER + WALLET_ID_1 + "?amount=5000",
                HttpMethod.PUT,
                new HttpEntity<>(body),
                Wallet.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        response = getWalletResponse();
        body = response.getBody();
        checkWallet(body, WALLET_1.getOperationType(), 10000L);
    }

    @Test
    void withdrawalWalletTest() {
        ResponseEntity<Wallet> response = createWalletResponse();
        Wallet body = response.getBody();
        body.setAmount(2500L);
        response = restTemplate.exchange(
                PATH_WITHDRAWAL + WALLET_ID_1 + "?amount=2500",
                HttpMethod.PUT,
                new HttpEntity<>(body),
                Wallet.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        response = getWalletResponse();
        body = response.getBody();
        checkWallet(body, WALLET_1.getOperationType(), 2500L);
    }

    @Test
    void withdrawalWalletNegativeTest() {
        ResponseEntity<Wallet> response = createWalletResponse();
        Wallet body = response.getBody();
        response = restTemplate.exchange(
                PATH_WITHDRAWAL + WALLET_ID_1 + "?amount=8000",
                HttpMethod.PUT,
                new HttpEntity<>(body),
                Wallet.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isFalse();
    }

    private void checkWallet(Wallet wallet, Wallet.OperationType operationType, Long amount) {
        assertThat(wallet).isNotNull();
        assertThat(wallet.getWalletId()).isNotNull();
        assertThat(wallet.getAmount()).isEqualTo(amount);
        assertThat(wallet.getOperationType()).isEqualTo(operationType);
    }

    private ResponseEntity<Wallet> createWalletResponse() {
        return restTemplate.postForEntity(PATH_WALLET, WALLET_1, Wallet.class);
    }

    private ResponseEntity<Wallet> getWalletResponse() {
        return restTemplate.getForEntity(PATH_GET + WALLET_ID_1, Wallet.class);
    }
}
