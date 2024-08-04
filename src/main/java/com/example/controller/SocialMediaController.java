package com.example.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {



    @PostMapping("/register")
    public ResponseEntity<Account> createAccount() {
        Account bingus = new Account();
        return ResponseEntity.status(200).body(bingus);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login() {
        Account bingus = new Account();
        return ResponseEntity.status(200).body(bingus);
    }

    @PostMapping("/messages") 
    public ResponseEntity<Message> createMessage() {
        Message msg = new Message();
        return ResponseEntity.status(200).body(msg);
    }

    @GetMapping("/messages") 
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> msgs = new ArrayList<Message>();
        return ResponseEntity.status(200).body(msgs);
    }
    
    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageByID() {
        Message msg = new Message();
        return ResponseEntity.status(200).body(msg);
    }

    @DeleteMapping("messages/{id}")
    public ResponseEntity<Message> deleteMessage() {
        Message msg = new Message();
        return ResponseEntity.status(200).body(msg);
    }

    @PatchMapping("messages/{id}")
    public ResponseEntity<Message> updateMessage() {
        Message msg = new Message();
        return ResponseEntity.status(200).body(msg);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser() {
        List<Message> msgs = new ArrayList<Message>();
        return ResponseEntity.status(200).body(msgs);
    }
}
