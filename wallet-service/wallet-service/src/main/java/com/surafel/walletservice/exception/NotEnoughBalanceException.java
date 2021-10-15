package com.surafel.walletservice.exception;

import com.surafel.walletservice.enums.FailureTypes;

public class NotEnoughBalanceException extends Throwable {
    public NotEnoughBalanceException() {
        super(String.valueOf(FailureTypes.NOT_ENOUGH_BALANCE));
    }
}
