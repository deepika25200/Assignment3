package com.example.demo.Repository;

import com.example.demo.model.Transaction;
import com.example.demo.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Integer>
{
    Optional<List<Transaction>> findByStatus(TransactionStatus transactionStatus);
}
