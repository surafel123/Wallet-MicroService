package com.surafel.walletservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_account_info")
public class UserAccountInfo {
    @Id
    private long userId;

    @Column(name = "current_balance")
    private double currentBalance;

    public UserAccountInfo() {

    }

    public UserAccountInfo(long userId, float currentBalance) {
        this.userId = userId;
        this.currentBalance = currentBalance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
