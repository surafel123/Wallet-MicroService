package com.surafel.walletservice.exception;

import com.surafel.walletservice.enums.FailureTypes;

public class UserAccountInfoNotFoundException extends Throwable {

    public UserAccountInfoNotFoundException() {
        super(String.valueOf(FailureTypes.USER_ACCOUNT_INFO_NOT_FOUND));
    }
}
