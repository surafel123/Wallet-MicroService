package com.surafel.walletservice.service;

import com.surafel.walletservice.enums.TransactionType;
import com.surafel.walletservice.exception.NotEnoughBalanceException;
import com.surafel.walletservice.exception.UserAccountInfoNotFoundException;

public interface UserAccountInfoService {
    double saveTransaction(double amount,
                           long userId,
                           long transactionId,
                           TransactionType transactionType)
            throws UserAccountInfoNotFoundException, NotEnoughBalanceException;
}
