package com.obpam.services;

import com.obpam.dtos.TransactionDto;
import com.obpam.errors.InvalidTransactionException;
import com.obpam.models.Account;
import com.obpam.models.Transaction;
import com.obpam.views.TransactionView;
import com.obpam.repositories.AccountRepository;
import com.obpam.repositories.TransactionRepository;
import com.obpam.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.obpam.StringConstants.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper mapper;

    @Autowired
    public TransactionServiceImpl(UserRepository userRepository,
                                  AccountRepository accountRepository,
                                  TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.mapper = new ModelMapper();
    }

    @Transactional
    @Override
    public List<TransactionView> getAllUserAccountTransactions(long userId, long accountId) {
        validateUserAndAccount(userId, accountId);
        return transactionRepository.getAllUserAccountTransactions(accountId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public TransactionDto createTransaction(long userId, long accountId, TransactionDto transaction) {
        validateUserAndAccount(userId, accountId);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_DOES_NOT_EXIST));

        Transaction transactionNew = new Transaction();
        mapper.map(transaction, transactionNew);
        transactionNew.setAccount(account);

        long balanceNew = calculateBalance(account.getBalance(), transactionNew);
        validateTransaction(balanceNew);

        account.setBalance(balanceNew);
        account.getTransactions().add(transactionNew);

        transactionRepository.save(transactionNew);
        accountRepository.save(account);

        mapper.map(transactionNew, transaction);
        return transaction;
    }

    private void validateUserAndAccount(long userId, long accountId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(USER_DOES_NOT_EXIST);
        }
        if (!accountRepository.existsByIdAndUserId(accountId, userId)) {
            throw new EntityNotFoundException(ACCOUNT_DOES_NOT_EXIST_OR_BELONG_TO_USER);
        }
    }

    private long calculateBalance(long oldValue, Transaction transaction) {
        return oldValue + (transaction.getType().getSign() * transaction.getAmount());
    }

    private void validateTransaction(long calculatedBalance) {
        if (calculatedBalance < 0) {
            throw new InvalidTransactionException(INSUFFICIENT_FUNDS);
        }
    }
}
