package com.example.demo.Repository;

import com.example.demo.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long>
{
    //Optional<Wallet> findByUserId(int userId);
}
