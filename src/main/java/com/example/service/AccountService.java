package com.example.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.entity.Account;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.AccountDoesNotExistException;
import com.example.exception.InvalidUsernamePasswordException;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account newAccount(Account account) throws AccountAlreadyExistsException, InvalidUsernamePasswordException{
        Optional<Account> existingAcc = accountRepository.findByUsername(account.getUsername());
        if(existingAcc.isPresent()) {
            throw new AccountAlreadyExistsException();
        } else if (account.getUsername().length() < 1 || account.getPassword().length() < 4){
            throw new InvalidUsernamePasswordException();
        }
        return accountRepository.save(account);
    }

    public Account login(Account account) throws AccountDoesNotExistException, InvalidUsernamePasswordException{
        Optional<Account> matchedAccOpt = accountRepository.findByUsername(account.getUsername());
        if(!matchedAccOpt.isPresent()){
            throw new AccountDoesNotExistException();
        }
        Account matchedAcc = matchedAccOpt.get();
        if(matchedAcc.getPassword().compareTo(account.getPassword()) == 0) {
            return matchedAcc;
        }
        throw new InvalidUsernamePasswordException();
    }
}
