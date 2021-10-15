package com.surafel.walletservice.repository;

import com.surafel.walletservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(long userId);
    Boolean existsByTransactionId(long transactionId);

}
