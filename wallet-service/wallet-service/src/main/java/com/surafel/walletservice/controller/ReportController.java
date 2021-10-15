package com.surafel.walletservice.controller;

import com.surafel.walletservice.model.Transaction;
import com.surafel.walletservice.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    TransactionsService transactionsService;

    /**
     * Get Transaction Reports
     *
     * @param userId The user Id.
     * @return Transactions of the user
     */
    @GetMapping("/{userId}")
    public List<Transaction> getReport(@PathVariable("userId") long userId) {
        return transactionsService.getReports(userId);
    }
}
