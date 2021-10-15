package com.surafel.walletservice.service;

import com.surafel.walletservice.model.Transaction;
import com.surafel.walletservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Boolean usedTransactionId(long transactionId) {
        return transactionRepository.existsByTransactionId(transactionId);
    }

    @Override
    @Transactional
    public List<Transaction> getReports(long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
