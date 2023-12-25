package com.example.demo.ServiceInterfaces;

import com.example.demo.model.Wallet;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface VendorServices {
    public ResponseEntity<String> registerVendor(Long id, double latitude, double longitude);
    public ResponseEntity<String> approveVendor(Long adminId, long vendorId, Long walletId);
    public ResponseEntity<String> transferMoney(Long vendorId, BigDecimal amount, Long personalWalletId);
}
