package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.entity.Account;

import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account newAccount(Account account){
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        Account dbMatchedAcc = accountRepository.findByUsername(username);
        if(dbMatchedAcc.getPassword().compareTo(password) == 0) {
            return dbMatchedAcc;
        }
        return null;
    }
}
