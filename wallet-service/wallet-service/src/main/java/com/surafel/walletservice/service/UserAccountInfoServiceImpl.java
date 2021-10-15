package com.surafel.walletservice.service;

import com.surafel.walletservice.enums.TransactionType;
import com.surafel.walletservice.exception.NotEnoughBalanceException;
import com.surafel.walletservice.exception.UserAccountInfoNotFoundException;
import com.surafel.walletservice.model.Transaction;
import com.surafel.walletservice.model.UserAccountInfo;
import com.surafel.walletservice.repository.TransactionRepository;
import com.surafel.walletservice.repository.UserAccountInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserAccountInfoServiceImpl implements UserAccountInfoService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserAccountInfoRepository userAccountInfoRepository;

    private Logger logger = LoggerFactory.getLogger(UserAccountInfoServiceImpl.class);

    /**
     * Save the Transaction if successful.
     * To prevent dirty reads, non-repeatable reads and phantom reads,
     * the Isolation level is complete isolation(SERIALIZABLE). It involves full locking.
     *
     * @param amount          The amount to be Debited or Credited.
     * @param userId          The user Id.
     * @param transactionType The transaction type (DEBIT or CREDIT)
     * @return The Balance and the Success type.
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public double saveTransaction(double amount,
                                  long userId,
                                  long transactionId,
                                  TransactionType transactionType)
            throws UserAccountInfoNotFoundException, NotEnoughBalanceException {
        Optional<UserAccountInfo> userAccountInfo = userAccountInfoRepository.findById(userId);

        double currentBalance = userAccountInfo
                .orElseThrow(UserAccountInfoNotFoundException::new)
                .getCurrentBalance();


        UserAccountInfo user = userAccountInfo.get();
        double newBalance = currentBalance;
        if (transactionType == TransactionType.CREDIT) {
            newBalance += amount;
            user.setCurrentBalance(newBalance);
            saveTransaction(user,
                    new Transaction(transactionId, transactionType, amount, userId));
        } else // DEBIT
        {
            if (currentBalance - amount >= 0) {
                newBalance -= amount;
                user.setCurrentBalance(newBalance);
                saveTransaction(user,
                        new Transaction(transactionId, transactionType, amount, userId));
            } else {
                logger.info("insufficient balance for transaction: userId: {}, transactionId: {}, amount: {}",
                        userId,
                        transactionId,
                        amount);
                throw new NotEnoughBalanceException();
            }
        }

        return newBalance;
    }

    @Transactional
    private void saveTransaction(UserAccountInfo userAccountInfo,
                                 Transaction transaction) {
        userAccountInfoRepository.save(userAccountInfo);
        transactionRepository.save(transaction);

        logger.info("successful {} transaction: userId: {}, with transactionId: {}, amount: {}",
                transaction.getTransactionType().toString(),
                userAccountInfo.getUserId(),
                transaction.getTransactionId(),
                transaction.getTransactionAmount());
    }
}
