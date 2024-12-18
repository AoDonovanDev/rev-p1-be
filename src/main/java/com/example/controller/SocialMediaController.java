package com.example.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;

import com.example.entity.AccInfoDto;
import com.example.entity.Account;
import com.example.entity.AuthDto;
import com.example.entity.Post;
import com.example.entity.PostDto;
import com.example.entity.PostLike;
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
    public @ResponseBody ResponseEntity<AuthDto> createAccount(@RequestBody Account account) {
        try {
            Account newAccount = accountService.createAccount(account);
            AuthDto authDto = accountService.login(newAccount);
            return ResponseEntity.status(200).body(authDto); 
        } catch (AccountAlreadyExistsException | AccountDoesNotExistException | InvalidUsernamePasswordException e) {
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
            Account account = accountService.getAccountInfo(token);
            return ResponseEntity.status(200).body(new AccInfoDto(true, account));
        } catch(Exception e){
            return ResponseEntity.status(500).body(new AccInfoDto(false, null));
        }
    }

    @PostMapping("/token")
    public @ResponseBody ResponseEntity<AuthDto> validateAndRefreshToken(@RequestBody String token){
        try {
            AuthDto authDto = accountService.validateAndRefresh(token);
            return ResponseEntity.status(200).body(authDto);
        } catch(Exception e){
            return ResponseEntity.status(500).body(new AuthDto(false, null));
        }
    }

    @PostMapping("/posts") 
    public @ResponseBody ResponseEntity<PostDto> createPost(@RequestBody Post post) {

        try {
            PostDto newPost = postService.createPost(post);
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

    @GetMapping("/accounts/{account_id}")
    public @ResponseBody ResponseEntity<AccInfoDto> getAccountByAccountId(@PathVariable Integer account_id){
        try{
            AccInfoDto accInfoDto = accountService.getAccountByAccountId(account_id);
            return ResponseEntity.status(200).body(accInfoDto);
        } catch(AccountDoesNotExistException e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(new AccInfoDto(false, null));   
        }
    }

    @PostMapping("/posts/addLike")
    public @ResponseBody ResponseEntity<Object> addLike(@RequestBody PostLike postLike){
        postService.addLike(postLike.getPlAccountId(), postLike.getPlPostId());
        return ResponseEntity.status(204).body(null);
    }

    @PostMapping("posts/removeLike")
    public @ResponseBody ResponseEntity<Object> removeLike(@RequestBody PostLike postLike){
        postService.removeLike(postLike.getPlAccountId(), postLike.getPlPostId());
        return ResponseEntity.status(204).body(null);
    }
}
