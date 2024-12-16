package com.example.service;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.AccInfoDto;
import com.example.entity.Account;
import com.example.entity.AccountIdStruct;
import com.example.entity.AuthDto;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.AccountDoesNotExistException;
import com.example.exception.AccountInfoException;
import com.example.exception.InvalidUsernamePasswordException;
import com.example.repository.AccountRepository;

import io.jsonwebtoken.JwtException;

@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;
    JwtService jwtService;

    @Autowired
    public AccountService(AccountRepository accountRepository, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
    }

    public Account createAccount(Account account) throws AccountAlreadyExistsException, InvalidUsernamePasswordException{
        Optional<Account> existingAcc = accountRepository.findByUsername(account.getUsername());
        if(existingAcc.isPresent()) {
            throw new AccountAlreadyExistsException();
        } else if (account.getUsername().length() < 1 || account.getPassword().length() < 4){
            throw new InvalidUsernamePasswordException();
        }
        return accountRepository.save(account);
    }

    public AuthDto login(Account account) throws AccountDoesNotExistException, InvalidUsernamePasswordException{
        Optional<Account> matchedAccOpt = accountRepository.findByUsername(account.getUsername());
        if(!matchedAccOpt.isPresent()){
            throw new AccountDoesNotExistException();
        }
        Account matchedAcc = matchedAccOpt.get();
        if(matchedAcc.getPassword().compareTo(account.getPassword()) == 0) {
            String token = jwtService.generateAccountToken(matchedAcc);
            AuthDto authDto = new AuthDto(true, token);
            return authDto;
        }
        throw new InvalidUsernamePasswordException();
    }

    public Account getAccountInfo(String token) throws JwtException, AccountInfoException{
        Integer accountId = jwtService.returnAccountIdFromToken(token);
        Account account = accountRepository.findByAccountId(accountId).get();
        System.out.println(account);
        return account;
    }

    public AuthDto validateAndRefresh(String token) throws JwtException{
        AccountIdStruct ais = jwtService.validateAndRefresh(token);
        Account acc = accountRepository.findByAccountId(ais.getAccountId()).get();
        String newToken = jwtService.generateAccountToken(acc);
        return new AuthDto(true, newToken);
    }

    public AccInfoDto getAccountByAccountId(Integer accountId) throws AccountDoesNotExistException{
        Optional<Account> accOpt = accountRepository.findByAccountId(accountId);
        if(accOpt.isPresent()){
            return new AccInfoDto(true, accOpt.get());
        }
        throw new AccountDoesNotExistException();
        
    }
}
