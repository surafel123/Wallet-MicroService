package com.surafel.walletservice.controller;

import com.surafel.walletservice.enums.TransactionType;
import com.surafel.walletservice.exception.NotEnoughBalanceException;
import com.surafel.walletservice.exception.TransactionIdUsedException;
import com.surafel.walletservice.exception.UserAccountInfoNotFoundException;
import com.surafel.walletservice.service.TransactionsService;
import com.surafel.walletservice.service.UserAccountInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet/credit")
public class CreditController {
    @Autowired
    UserAccountInfoService userAccountInfoService;

    @Autowired
    TransactionsService transactionsService;

    private Logger logger = LoggerFactory.getLogger(CreditController.class);

    /**
     * @param userId        The user Id.
     * @param transactionId The Unique Id of the transaction.
     * @param amount        The amount to be credited to the user.
     * @return new balance after credit is returned for successful transaction
     * @throws TransactionIdUsedException thrown when the Id is already used
     */
    @PostMapping("/{userId}")
    public double credit(@PathVariable("userId") long userId,
                         @RequestParam("transactionId") long transactionId,
                         @RequestParam("amount") float amount)
            throws TransactionIdUsedException, NotEnoughBalanceException, UserAccountInfoNotFoundException {

        logger.info("credit transaction requested for userId: {}, with transactionId: {}, amount: {}",
                userId, transactionId, amount);

        if (transactionsService.usedTransactionId(transactionId)) {
            logger.error("usedTransactionId for userId: {}, with transactionId: {}",
                    userId, transactionId);
            throw new TransactionIdUsedException();
        } else {
            return userAccountInfoService.saveTransaction(
                    amount,
                    userId,
                    transactionId,
                    TransactionType.CREDIT
                    
            );
        }
    }
}