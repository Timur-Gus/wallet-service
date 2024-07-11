package com.gusainovt.wallets_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gusainovt.wallets_service.model.Wallet;
import com.gusainovt.wallets_service.repositories.WalletRepository;
import com.gusainovt.wallets_service.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static com.gusainovt.wallets_service.constants.TestConstants.*;
import static com.gusainovt.wallets_service.model.Wallet.OperationType.WITHDRAW;


@WebMvcTest(controllers = WalletController.class)
class WalletControllerTest {
    @MockBean
    WalletRepository walletRepository;
    @SpyBean
    WalletService walletService;
    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    private Wallet wallet;

    @BeforeEach
    void init() {
        wallet = WALLET_1;
        Optional<Wallet> optionalWallet1 = Optional.of(wallet);
        Mockito.when(walletRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(optionalWallet1);
    }

    @Test
    void createWallet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH_WALLET)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(wallet)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(walletRepository).save(ArgumentMatchers.any(Wallet.class));
    }

    @Test
    void editWallet() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.PUT, PATH_WALLET + "/" + WALLET_ID_1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(wallet)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(walletRepository).save(ArgumentMatchers.any(Wallet.class));
    }

    @Test
    void transferWallet() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.PUT, PATH_TRANSFER + WALLET_ID_1 + "?amount=1000")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(wallet)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(walletRepository).save(ArgumentMatchers.any(Wallet.class));
    }

    @Test
    void withdrawalWallet() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.PUT, PATH_WITHDRAWAL + WALLET_ID_1 + "?amount=1000")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(wallet)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(walletRepository).save(ArgumentMatchers.any(Wallet.class));
    }

    @Test
    void getWallet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH_GET + WALLET_ID_1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(wallet)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("operationType").value(WITHDRAW.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("amount").value(5000));
    }
}