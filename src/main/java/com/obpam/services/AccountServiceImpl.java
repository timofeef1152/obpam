package com.obpam.services;

import com.obpam.dtos.AccountDto;
import com.obpam.models.Account;
import com.obpam.models.User;
import com.obpam.repositories.AccountRepository;
import com.obpam.repositories.UserRepository;
import com.obpam.views.AccountView;
import com.obpam.views.UserTotalBalance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.obpam.StringConstants.*;

@Service
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper mapper;

    @Autowired
    public AccountServiceImpl(UserRepository userRepository,
                              AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.mapper = new ModelMapper();
    }

    @Transactional
    @Override
    public AccountView getUserAccount(long userId, long accountId) {
        validateUserAndAccount(userId, accountId);
        return accountRepository.findOneById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_DOES_NOT_EXIST));
    }

    @Transactional
    @Override
    public List<AccountView> getAllUserAccounts(long userId) {
        validateUser(userId);
        return accountRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public UserTotalBalance getAllUserAccountsBalance(long userId) {
        validateUser(userId);
        return accountRepository.getTotalBalanceById(userId);
    }

    @Transactional
    @Override
    public AccountDto addAccountToUser(long userId, AccountDto account) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));

        Account accountNew = new Account();
        mapper.map(account, accountNew);
        accountNew.setUser(user);
        user.getAccounts().add(accountNew);

        accountRepository.save(accountNew);
        userRepository.save(user);

        mapper.map(accountNew, account);
        return account;
    }

    @Transactional
    @Override
    public void removeUserAccount(long userId, long accountId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_DOES_NOT_EXIST));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_DOES_NOT_EXIST));

        user.getAccounts().remove(account);
        accountRepository.deleteById(accountId);
        userRepository.save(user);
    }

    private void validateUser(long userId) throws EntityNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(USER_DOES_NOT_EXIST);
        }
        if (!accountRepository.existsByUserId(userId)) {
            throw new EntityNotFoundException(USER_DOES_NOT_HAVE_ACCOUNT);
        }
    }

    private void validateUserAndAccount(long userId, long accountId) throws EntityNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException(USER_DOES_NOT_EXIST);
        }
        if (!accountRepository.existsByIdAndUserId(accountId, userId)) {
            throw new EntityNotFoundException(ACCOUNT_DOES_NOT_EXIST_OR_BELONG_TO_USER);
        }
    }
}
