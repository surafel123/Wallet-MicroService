package com.surafel.walletservice.model;
import java.util.List;

public class TransactionsList {


    long userId;
    List<TransactionsList> TransactionList;

    public TransactionsList()
    {

    }

    public TransactionsList(long userId, List<TransactionsList> TransactionList)
    {
        this.userId = userId;
        this.TransactionList = TransactionList;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public List<TransactionsList> getTransactions()
    {
        return TransactionList;
    }

    public void setTransactions(List<TransactionsList> TransactionList)
    {
        this.TransactionList = TransactionList;
    }
}
