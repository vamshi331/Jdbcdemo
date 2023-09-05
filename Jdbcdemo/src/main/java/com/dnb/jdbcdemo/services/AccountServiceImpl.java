package com.dnb.jdbcdemo.services;

import com.dnb.jdbcdemo.dto.Account;
import com.dnb.jdbcdemo.repo.AccountRepository;
import com.dnb.jdbcdemo.repo.AccountRepositoryImpl;


import java.util.Optional;



public class AccountServiceImpl implements AccountService {

    private static AccountService accountService = null;
    private AccountServiceImpl(){

    }

    public static AccountService getInstance(){
        synchronized (AccountServiceImpl.class){
            if (accountService == null){
                accountService = new AccountServiceImpl();
            }
        }

        return accountService;
    }
    @Override
    public Account createAccount(Account account) {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
        return accountRepository.createAccount(account);
    }

    @Override
    public Optional<Account> getAccountById(String accountId) {
        AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
       return accountRepository.getAccountById(accountId);

    }
}
