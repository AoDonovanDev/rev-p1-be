package com.example.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.AccountDoesNotExistException;
import com.example.exception.InvalidMessageException;
import com.example.exception.InvalidUsernamePasswordException;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;


    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account newAccount = accountService.createAccount(account);
            return ResponseEntity.status(200).body(newAccount); 
        } catch (AccountAlreadyExistsException | InvalidUsernamePasswordException e) {
            e.printStackTrace();
            if(e.getClass().getName().contains("AccountAlreadyExistsException")){
                return ResponseEntity.status(409).body(null);
            } else {
                return ResponseEntity.status(400).body(null);
            }
        }


    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) {
        
        try {
            Account authedAcc = accountService.login(account);
            return ResponseEntity.status(200).body(authedAcc);
        } catch (AccountDoesNotExistException | InvalidUsernamePasswordException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/messages") 
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message) {

        try {
            Message newMessage = messageService.creatMessage(message);
            return ResponseEntity.status(200).body(newMessage);
        } catch (InvalidMessageException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }

        
    }

    @GetMapping("/messages") 
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {
        List<Message> msgs = messageService.getAllMessages();
        return ResponseEntity.status(200).body(msgs);
    }
    
    @GetMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Message> getMessageByID(@PathVariable Integer id) {
        Optional<Message> messageOpt = messageService.getMessageById(id);
        if(messageOpt.isPresent()) {
            return ResponseEntity.status(200).body(messageOpt.get());
        }
        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable Integer id) {
        Optional<Message> messageOpt = messageService.deleteMessage(id);
        if(messageOpt.isPresent()) {
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/messages/{id}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@PathVariable Integer id, @RequestBody Message message) {
        try {
            messageService.updateMessage(id, message.getMessageText());
            return ResponseEntity.status(200).body(1);
        } catch (InvalidMessageException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer account_id) {
        List<Message> msgs = messageService.getMessagesByUser(account_id);
        return ResponseEntity.status(200).body(msgs);
    }
}
