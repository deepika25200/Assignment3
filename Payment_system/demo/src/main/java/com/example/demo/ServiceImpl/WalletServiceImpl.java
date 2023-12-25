package com.example.demo.ServiceImpl;

import com.example.demo.Exceptions.UserNotFoundExeption;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.WalletRepository;
import com.example.demo.ServiceInterfaces.UserServices;
import com.example.demo.ServiceInterfaces.WalletServices;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WalletServiceImpl implements WalletServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;
    @Override
    public ResponseEntity<String> addMoneyToWallet(int userid, BigDecimal amount) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        Wallet wallet = user.getWallet();
        if (wallet == null) {
            wallet = new Wallet(user);
            user.setWallet(wallet);
        }
        wallet.setWalletBalance(wallet.getWalletBalance().add(amount));
        walletRepository.save(wallet);
        return ResponseEntity.ok(amount +" Money added to wallet successfully.");
    }
    public ResponseEntity<BigDecimal> checkWalletBalance(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        Wallet wallet = user.getWallet();
        if (wallet == null) {
            return ResponseEntity.ok(new BigDecimal(0));
        }
        return ResponseEntity.ok(wallet.getWalletBalance());
    }
    @Override
    public ResponseEntity<String> addBalanceToOffline(int userId,BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        Wallet wallet = user.getWallet();
        if (wallet == null) {
            return ResponseEntity.badRequest().body("Wallet is not found for the user first add some money and create your wallet by url http://localhost:8080/bankingapi/wallet/add-money/{your id}?amount={amount-need-to-be-added}");
        }
        if (wallet.getWalletBalance().compareTo(amount)<0) {
            return ResponseEntity.badRequest().body("Insufficient funds in the wallet. Add money by url http://localhost:8080/bankingapi/wallet/add-money/{your id}?amount={amount-need-to-be-added}");
        }
        if(wallet.getCodes_for_transaction().isEmpty())
            generateCodes(wallet);
        if(wallet.getAmountAvailableOnOffline().compareTo(new BigDecimal(5000))<=0) {

            wallet.setWalletBalance(wallet.getWalletBalance().subtract(amount));
            wallet.setAmountAvailableOnOffline(wallet.getAmountAvailableOnOffline().add(amount));
            walletRepository.save(wallet);
            return ResponseEntity.ok(amount + " Money transferred successfully to offline balance succesfully");
        }
        return ResponseEntity.badRequest().body("Offline balnce shouldn't exceed 5000");
    }
    private void generateCodes(Wallet wallet) {
        for (int i = 0; i <5; i++) {
            String randomCode = generateRandomCode();
            wallet.getCodes_for_transaction().add(randomCode);
        }
    }
    private String generateRandomCode() {
        int codeLength = 8;
        return RandomStringUtils.randomAlphanumeric(codeLength);
    }
    public ResponseEntity<Set<String>> getWalletCodes(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        Wallet wallet = user.getWallet();
        if (wallet == null || wallet.getCodes_for_transaction().isEmpty()) {
            return ResponseEntity.ok(new HashSet<>());
        }
        return ResponseEntity.ok(wallet.getCodes_for_transaction());
    }

}
