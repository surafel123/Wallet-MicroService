package com.surafel.walletservice.service;

import com.surafel.walletservice.model.Transaction;

import java.util.List;

public interface TransactionsService {
    Boolean usedTransactionId(long transactionId);
    List<Transaction> getReports(long userId);
}
