package com.surafel.walletservice.exception;

import com.surafel.walletservice.enums.FailureTypes;

public class TransactionIdUsedException extends Throwable {
    public TransactionIdUsedException() {
        super(String.valueOf(FailureTypes.TRANSACTION_ID_ALREADY_USED));
    }
}
