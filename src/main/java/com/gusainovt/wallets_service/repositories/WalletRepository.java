package com.gusainovt.wallets_service.repositories;

import com.gusainovt.wallets_service.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

}
