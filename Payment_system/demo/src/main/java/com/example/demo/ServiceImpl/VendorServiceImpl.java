package com.example.demo.ServiceImpl;

import com.example.demo.Exceptions.AdminNotFoundException;
import com.example.demo.Exceptions.VendorNotFoundException;
import com.example.demo.Repository.AdminRepository;
import com.example.demo.Repository.VendorRepository;
import com.example.demo.ServiceInterfaces.VendorServices;
import com.example.demo.model.Admin;
import com.example.demo.model.Vendor;
import com.example.demo.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class VendorServiceImpl implements VendorServices {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Override
    public ResponseEntity<String> registerVendor(Long id, double latitude, double longitude) {
        Vendor vendor=new Vendor(id,latitude,longitude);
        vendorRepository.save(vendor);
        return ResponseEntity.ok("Vendor registration request submitted. Waiting for approval.");
    }
    @Override
    public ResponseEntity<String> approveVendor(Long adminId, long vendorId,Long walletId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found"));
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
        Wallet storeWallet = vendor.getStoreWallet();
        if (storeWallet == null) {
            storeWallet = new Wallet(walletId);
            vendor.setStoreWallet(storeWallet);
        }
        vendor.setStatus(true); // Approve the vendor
        vendorRepository.save(vendor);
        return ResponseEntity.ok("Vendor approved successfully by admin "+admin.getName());
    }

    @Override
    public ResponseEntity<String> transferMoney(Long vendorId, BigDecimal amount, Long personalWalletId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found"));

        if (vendor.getStoreWallet() == null) {
            return ResponseEntity.badRequest().body("Store wallet not found for the vendor.");
        }
        Wallet personalWallet = vendor.getPersonalWallet();
        if (personalWallet == null) {
            personalWallet = new Wallet(personalWalletId);
            vendor.setPersonalWallet(personalWallet);
        }
        vendor.getStoreWallet().setWalletBalance(vendor.getStoreWallet().getWalletBalance().subtract(amount));
        vendor.getPersonalWallet().setWalletBalance(vendor.getPersonalWallet().getWalletBalance().add(amount));
        vendorRepository.save(vendor);
        return ResponseEntity.ok(amount+" Money transferred from store wallet to personal wallet.");
    }

}
