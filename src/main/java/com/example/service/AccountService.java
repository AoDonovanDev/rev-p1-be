package com.example.service;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.AccInfoDto;
import com.example.entity.Account;
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

    public AccInfoDto getAccountInfo(String token) throws JwtException, AccountInfoException{

        String accJson = jwtService.returnAccountJson(token);
        
        return new AccInfoDto(true, accJson);
    }
}
