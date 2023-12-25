package com.example.demo.ServiceImpl;
import com.example.demo.Repository.*;
import com.example.demo.ServiceInterfaces.TransactionServices;
import com.example.demo.TransactionsSupport.PaymentRequestOffline;
import com.example.demo.TransactionsSupport.PaymentRequestOnline;
import com.example.demo.model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import com.example.demo.model.*;
import java.util.List;

import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionServices {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Override
    public ResponseEntity<String> makePayment(PaymentRequestOnline paymentRequest) {
        User user = userRepository.findById(paymentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vendor vendor = vendorRepository.findById((long) paymentRequest.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        // Check if the payment is within 20km radius
        if (!isWithinRadius(paymentRequest.getLatitude(), paymentRequest.getLongitude(), vendor.getLatitude(), vendor.getLongitude(), 20)) {
            Transaction transaction = new Transaction();
            transaction.setUserId(paymentRequest.getUserId());
            transaction.setVendorId(paymentRequest.getVendorId());
            transaction.setAmount(paymentRequest.getAmount());
            transaction.setStatus(TransactionStatus.FLAGGED);
            transaction.setPaymentMode(PaymentMode.ONLINE);
            transaction.setTransactionDate(new Date());
            Wallet userWallet = user.getWallet();
            userWallet.setWalletBalance(userWallet.getWalletBalance().subtract(paymentRequest.getAmount()));
            walletRepository.save(userWallet);
            transactionRepository.save(transaction);
            return ResponseEntity.ok("Payment flagged. Payment made from > 20 km");
        } else {
            Transaction transaction = new Transaction();
            transaction.setUserId(paymentRequest.getUserId());
            transaction.setVendorId(paymentRequest.getVendorId());
            transaction.setAmount(paymentRequest.getAmount());
            transaction.setStatus(TransactionStatus.SUCCESSFUL);
            transaction.setPaymentMode(PaymentMode.ONLINE);
            transaction.setTransactionDate(new Date());
            Wallet userWallet = user.getWallet();
            userWallet.setWalletBalance(userWallet.getWalletBalance().subtract(paymentRequest.getAmount()));
            walletRepository.save(userWallet);
            Wallet vendorWallet = vendor.getStoreWallet();
            vendorWallet.setWalletBalance((vendorWallet.getWalletBalance().add(paymentRequest.getAmount())));
            walletRepository.save(vendorWallet);
            transactionRepository.save(transaction);
            return ResponseEntity.ok("Payment successful.");
        }
    }
    @Override
    public ResponseEntity<String> makePaymentOffline(PaymentRequestOffline paymentRequestOffline) {
        User user = userRepository.findById(paymentRequestOffline.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the provided code matches any of the codes in the user's set
        if (!user.getWallet().getCodes_for_transaction().contains(paymentRequestOffline.getCode())) {
            return ResponseEntity.badRequest().body("Invalid code. Transaction failed.");
        }

        Vendor vendor = vendorRepository.findById((long)paymentRequestOffline.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        if (!isWithinRadius(paymentRequestOffline.getLatitude(), paymentRequestOffline.getLongitude(), vendor.getLatitude(), vendor.getLongitude(), 20)) {

            Transaction transaction = new Transaction();
            transaction.setUserId(paymentRequestOffline.getUserId());
            transaction.setVendorId(paymentRequestOffline.getVendorId());
            transaction.setAmount(paymentRequestOffline.getAmount());
            transaction.setStatus(TransactionStatus.FLAGGED);
            transaction.setPaymentMode(PaymentMode.OFFLINE);
            transaction.setTransactionDate(new Date());
            Wallet userWallet = user.getWallet();
            userWallet.setAmountAvailableOnOffline(userWallet.getAmountAvailableOnOffline().subtract(paymentRequestOffline.getAmount()));
            walletRepository.save(userWallet);
            transactionRepository.save(transaction);
            return ResponseEntity.ok("payment flagged. distance > 20km");
        } else {

            Transaction transaction = new Transaction();
            transaction.setUserId(paymentRequestOffline.getUserId());
            transaction.setVendorId(paymentRequestOffline.getVendorId());
            transaction.setAmount(paymentRequestOffline.getAmount());
            transaction.setStatus(TransactionStatus.SUCCESSFUL);
            transaction.setPaymentMode(PaymentMode.OFFLINE);
            transaction.setTransactionDate(new Date());
            Wallet userWallet = user.getWallet();
            userWallet.setAmountAvailableOnOffline(userWallet.getAmountAvailableOnOffline().subtract(paymentRequestOffline.getAmount()));
            walletRepository.save(userWallet);
            Wallet vendorWallet = vendor.getStoreWallet();
            vendorWallet.setAmountAvailableOnOffline((vendorWallet.getWalletBalance().add(paymentRequestOffline.getAmount())));
            walletRepository.save(vendorWallet);
            transactionRepository.save(transaction);
            return ResponseEntity.ok("Offline payment successful.");
        }

    }
    private boolean isWithinRadius(double lat1, double lon1, double lat2, double lon2, double radius) {
        double earthRadius = 6371; // in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = earthRadius * c;

        return distance <= radius;
    }
    @Override
    public ResponseEntity<List<Transaction>> getFlaggedTransactions() {
        // Retrieve flagged transactions for admin review
        List<Transaction> flaggedTransactions = transactionRepository.findByStatus(TransactionStatus.FLAGGED).get();
        return ResponseEntity.ok(flaggedTransactions);
    }
    @Override
    public ResponseEntity<String> reviewTransaction(Long adminId, Long transactionId, Boolean approval) {
        // Implement logic for admin review
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Transaction transaction = transactionRepository.findById(Integer.valueOf(Math.toIntExact(transactionId)))
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        if (transaction.getStatus() == TransactionStatus.FLAGGED) {
            if (approval) {
                transferAmountToVendor(transaction);
            } else {
                returnAmountToUser(transaction);
            }
        }
        return ResponseEntity.ok("Transaction reviewed successfully.");
    }
    private void transferAmountToVendor(Transaction transaction) {
        Vendor vendor = vendorRepository.findById((long)transaction.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        Wallet vendorWallet = vendor.getStoreWallet();
        vendorWallet.setWalletBalance(vendorWallet.getWalletBalance().add(transaction.getAmount()));
        walletRepository.save(vendorWallet);
        transaction.setStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.save(transaction);
    }
    private void returnAmountToUser(Transaction transaction) {
        User user = userRepository.findById(transaction.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Return amount to user's wallet
        Wallet userWallet = user.getWallet();
        userWallet.setWalletBalance(userWallet.getWalletBalance().add(transaction.getAmount()));

        // Save the updated user's wallet
        walletRepository.save(userWallet);

        // Update the transaction status to FAILED
        transaction.setStatus(TransactionStatus.FAILED);
        transactionRepository.save(transaction);
    }



}
