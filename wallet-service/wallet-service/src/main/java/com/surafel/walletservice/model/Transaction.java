package com.surafel.walletservice.model;

import com.surafel.walletservice.enums.TransactionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    private long transactionId;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "transaction_amount")
    private double transactionAmount;

    @Column(name = "user_id")
    private long userId;

    public Transaction() {
    }

    public Transaction(long transactionId,
                       TransactionType type,
                       double transactionAmount,
                       long userId) {
        this.transactionId = transactionId;
        this.transactionType = type;
        this.transactionAmount = transactionAmount;
        this.userId = userId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
