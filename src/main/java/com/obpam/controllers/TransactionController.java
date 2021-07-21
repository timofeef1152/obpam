package com.obpam.controllers;

import com.obpam.dtos.TransactionDto;
import com.obpam.services.TransactionService;
import com.obpam.views.TransactionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.obpam.StringConstants.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping(USER + "/{userId}" + ACCOUNT + "/{accountId}" + TRANSACTIONS)
    public List<TransactionView> getAllUserAccountTransactions(@PathVariable("userId") long userId,
                                                               @PathVariable("accountId") long accountId) {
        return transactionService.getAllUserAccountTransactions(userId, accountId);
    }

    @PostMapping(USER + "/{userId}" + ACCOUNT + "/{accountId}" + TRANSACTION)
    public TransactionDto createTransactionForUserAccount(@PathVariable("userId") long userId,
                                                @PathVariable("accountId") long accountId,
                                                @RequestBody @Valid TransactionDto transaction) {
        return transactionService.createTransaction(userId, accountId, transaction);
    }
}
