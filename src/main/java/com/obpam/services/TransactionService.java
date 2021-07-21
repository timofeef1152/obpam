package com.obpam.services;

import com.obpam.dtos.TransactionDto;
import com.obpam.views.TransactionView;

import java.util.List;

public interface TransactionService {
    List<TransactionView> getAllUserAccountTransactions(long userId, long accountId);

    TransactionDto createTransaction(long userId, long accountId, TransactionDto transaction);
}
