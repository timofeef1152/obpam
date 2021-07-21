package com.obpam.services;

import com.obpam.dtos.AccountDto;
import com.obpam.views.AccountView;
import com.obpam.views.UserTotalBalance;

import java.util.List;

public interface AccountService {
    AccountView getUserAccount(long userId, long accountId);
    List<AccountView> getAllUserAccounts(long userId);
    UserTotalBalance getAllUserAccountsBalance(long userId);
    AccountDto addAccountToUser(long userId, AccountDto account);
    void removeUserAccount(long userId, long accountId);
}
