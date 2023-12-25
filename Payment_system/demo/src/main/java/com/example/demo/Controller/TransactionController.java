package com.example.demo.Controller;

import com.example.demo.ServiceInterfaces.TransactionServices;
import com.example.demo.TransactionsSupport.PaymentRequestOffline;
import com.example.demo.TransactionsSupport.PaymentRequestOnline;
import com.example.demo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankingapi/transations")
public class TransactionController {
    @Autowired
    TransactionServices transactionServices;
    @PostMapping("/online-payment")
    public ResponseEntity<String> makePayment(@RequestBody PaymentRequestOnline paymentRequest)
    {
        return transactionServices.makePayment(paymentRequest);
    }
    @PostMapping("/offline-payment")
    public ResponseEntity<String> makePaymentOffline(@RequestBody PaymentRequestOffline paymentRequestOffline)
    {
        return  transactionServices.makePaymentOffline(paymentRequestOffline);
    }
    @GetMapping("/flagged-transactions")
    public ResponseEntity<List<Transaction>> getFlaggedTransactions()
    {
        return  transactionServices.getFlaggedTransactions();
    }
    @PostMapping("/review-transaction/{adminId}/{transactionId}/{approval}")
    public ResponseEntity<String> reviewTransaction(
            @PathVariable Long adminId,
            @PathVariable Long transactionId,
            @PathVariable Boolean approval) {
        return transactionServices.reviewTransaction(adminId,transactionId,approval);
    }

}
