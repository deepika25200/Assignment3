package com.example.demo.ServiceInterfaces;

import com.example.demo.model.Wallet;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Set;
public interface WalletServices {


    ResponseEntity<String> addMoneyToWallet(int userid, BigDecimal amount);
    ResponseEntity<BigDecimal> checkWalletBalance(int userid);
    ResponseEntity<String> addBalanceToOffline(int userId,BigDecimal amount);
    ResponseEntity<Set<String>> getWalletCodes(int userId);
}
