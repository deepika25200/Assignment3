package com.example.demo.ServiceInterfaces;

import com.example.demo.TransactionsSupport.PaymentRequestOffline;
import com.example.demo.TransactionsSupport.PaymentRequestOnline;
import com.example.demo.model.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionServices {
    public ResponseEntity<String> makePayment(PaymentRequestOnline paymentRequest);
    public ResponseEntity<String> makePaymentOffline(PaymentRequestOffline paymentRequestOffline);
    public ResponseEntity<List<Transaction>> getFlaggedTransactions();
    public ResponseEntity<String> reviewTransaction(Long adminId, Long transactionId, Boolean approval);
}
