package com.obpam.controllers;

import com.obpam.dtos.AccountDto;
import com.obpam.services.AccountService;
import com.obpam.views.AccountView;
import com.obpam.views.UserTotalBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.obpam.StringConstants.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(USER + "/{userId}" + ACCOUNT + "/{accountId}")
    public AccountView getUserAccount(@PathVariable("userId") long userId,
                                      @PathVariable("accountId") long accountId) {
        return accountService.getUserAccount(userId, accountId);
    }

    @GetMapping(USER + "/{userId}" + ACCOUNTS)
    public List<AccountView> getAllUserAccounts(@PathVariable("userId") long userId) {
        return accountService.getAllUserAccounts(userId);
    }

    @GetMapping(USER + "/{userId}" + ACCOUNTS + "/balance")
    public UserTotalBalance getAllUserAccountsBalance(@PathVariable("userId") long userId) {
        return accountService.getAllUserAccountsBalance(userId);
    }

    @PostMapping(USER + "/{userId}" + ACCOUNT)
    public AccountDto addAccountToUser(@PathVariable("userId") long userId,
                                       @RequestBody @Valid AccountDto account) {
        return accountService.addAccountToUser(userId, account);
    }

    @DeleteMapping(USER + "/{userId}" + ACCOUNT + "/{accountId}")
    public void removeUserAccount(@PathVariable("userId") long userId,
                                  @PathVariable("accountId") long accountId) {
        accountService.removeUserAccount(userId, accountId);
    }
}
