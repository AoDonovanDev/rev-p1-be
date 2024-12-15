package com.example.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import com.example.entity.Account;
import com.example.exception.AccountInfoException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JwtService {

    private SecretKey key = Jwts.SIG.HS256.key().build();

    @Autowired
    ObjectMapper om;
    public JwtService(){
    }

    public String generateAccountToken(Account account){
        String jws = Jwts.builder()
        .claim("accountInfo", account)
        .signWith(key).compact();
        return jws;
    }

    public String returnAccountJson(String token) throws JwtException, AccountInfoException{
        try {
            Claims claimsJson = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
            String accJson = om.writeValueAsString(claimsJson);
            return accJson;
        } catch(Exception jwtException){
            jwtException.printStackTrace();
        }
        throw new AccountInfoException();
        
    }
}