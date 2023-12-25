package com.example.demo.Controller;
import com.example.demo.ServiceInterfaces.WalletServices;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/bankingapi/wallet")
public class WalletController {

    @Autowired
    private WalletServices walletServices;
    @PostMapping("/add-money/{userId}")
    public ResponseEntity<String> addMoneyToWallet(
            @PathVariable("userId") int userId,
            @RequestParam("amount") BigDecimal amount) {
            return walletServices.addMoneyToWallet(userId,amount);
    }
    @GetMapping("/get-balnce/{userId}")
    public ResponseEntity<BigDecimal> checkWalletBalance(@PathVariable("userId") int userId)
    {
        return walletServices.checkWalletBalance(userId);
    }
    @GetMapping("/add-offline-balance/{userId}")
    public ResponseEntity<String> addOfflineBalance(
            @PathVariable("userId") int userId,
            @RequestParam("amount") BigDecimal amount) {
        // Your logic to add offline balance
        return walletServices.addBalanceToOffline(userId, amount);
    }
    @GetMapping("/get-codes/{userOId}")
    public ResponseEntity<Set<String>> getCodes(@PathVariable("userId") int userId)
    {
        return walletServices.getWalletCodes(userId);
    }

}
