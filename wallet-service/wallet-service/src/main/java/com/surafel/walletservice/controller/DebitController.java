package com.surafel.walletservice.controller;

import com.surafel.walletservice.exception.NotEnoughBalanceException;
import com.surafel.walletservice.exception.TransactionIdUsedException;
import com.surafel.walletservice.exception.UserAccountInfoNotFoundException;
import com.surafel.walletservice.enums.TransactionType;
import com.surafel.walletservice.service.TransactionsService;
import com.surafel.walletservice.service.UserAccountInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet/debit")
public class DebitController
{
    @Autowired
    UserAccountInfoService userAccountInfoService;

    @Autowired
    TransactionsService transactionsService;

    private Logger logger = LoggerFactory.getLogger(DebitController.class);

    /**
     * Make a DEBIT Transaction
     *
     * @param userId The user Id.
     * @param transactionId The Unique Id of the transaction.
     * @param amount The amount to be debited from the user.
     * @return
     * @throws TransactionIdUsedException
     * @throws NotEnoughBalanceException
     * @throws UserAccountInfoNotFoundException
     */
    @PostMapping("/{userId}")
    public double debit(@PathVariable("userId") long userId,
                        @RequestParam("transactionId") long transactionId,
                        @RequestParam("amount") float amount)
            throws TransactionIdUsedException, NotEnoughBalanceException, UserAccountInfoNotFoundException
    {

        logger.info("debit transaction requested for userId: {}, with transactionId: {}, amount: {}",
                userId, transactionId, amount);

        if(transactionsService.usedTransactionId(transactionId)) {
            logger.error("usedTransactionId for userId: {}, with transactionId: {}",
                    userId, transactionId);
            throw new TransactionIdUsedException();
        }
        else {
            return userAccountInfoService.saveTransaction(
                    amount,
                    userId,
                    transactionId,
                    TransactionType.DEBIT
            );
        }
    }
}
