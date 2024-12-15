package com.example.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.AccInfoDto;
import com.example.entity.Account;
import com.example.entity.AuthDto;
import com.example.entity.Post;
import com.example.exception.AccountAlreadyExistsException;
import com.example.exception.AccountDoesNotExistException;
import com.example.exception.InvalidPostException;
import com.example.exception.InvalidUsernamePasswordException;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.PostService;


@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    PostService postService;


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
    public @ResponseBody ResponseEntity<AuthDto> login(@RequestBody Account account) {
        
        try {
            AuthDto authDto = accountService.login(account);
            return ResponseEntity.status(200).body(authDto);
        } catch (AccountDoesNotExistException | InvalidUsernamePasswordException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new AuthDto(false, null));
        }
    }

    @PostMapping("/accountInfo")
    public @ResponseBody ResponseEntity<AccInfoDto> getUserInfo(@RequestBody String token){
        try {
            AccInfoDto accJson = accountService.getAccountInfo(token);
            return ResponseEntity.status(200).body(accJson);
        } catch(Exception e){
            return ResponseEntity.status(500).body(new AccInfoDto(false, null));
        }
    }

    @PostMapping("/posts") 
    public @ResponseBody ResponseEntity<Post> createPost(@RequestBody Post post) {

        try {
            Post newPost = postService.createPost(post);
            return ResponseEntity.status(200).body(newPost);
        } catch (InvalidPostException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }

        
    }

    @GetMapping("/posts") 
    public @ResponseBody ResponseEntity<List<Post>> getAllPosts() {
        List<Post> msgs = postService.getAllPosts();
        return ResponseEntity.status(200).body(msgs);
    }
    
    @GetMapping("/posts/{id}")
    public @ResponseBody ResponseEntity<Post> getPostByID(@PathVariable Integer id) {
        Optional<Post> postOpt = postService.getPostById(id);
        if(postOpt.isPresent()) {
            return ResponseEntity.status(200).body(postOpt.get());
        }
        return ResponseEntity.status(200).body(null);
    }

    @DeleteMapping("/posts/{id}")
    public @ResponseBody ResponseEntity<Integer> deletePost(@PathVariable Integer id) {
        Optional<Post> postOpt = postService.deletePost(id);
        if(postOpt.isPresent()) {
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/posts/{id}")
    public @ResponseBody ResponseEntity<Integer> updatePost(@PathVariable Integer id, @RequestBody Post post) {
        try {
            postService.updatePost(id, post.getPostText());
            return ResponseEntity.status(200).body(1);
        } catch (InvalidPostException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/accounts/{account_id}/posts")
    public @ResponseBody ResponseEntity<List<Post>> getPostsByUser(@PathVariable Integer account_id) {
        List<Post> msgs = postService.getPostsByUser(account_id);
        return ResponseEntity.status(200).body(msgs);
    }
}
