package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Post;
import com.example.exception.InvalidPostException;
import com.example.repository.AccountRepository;
import com.example.repository.PostRepository;

@Service
@Transactional
public class PostService {

    PostRepository postRepository;

    AccountRepository accountRepository;

    @Autowired
    public PostService(PostRepository postRepository, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    public Post creatPost(Post post) throws InvalidPostException{
        Boolean validAccount = accountRepository.existsById(post.getPostedBy());
        if(post.getPostText().length() > 255 || post.getPostText().length() == 0 || !validAccount) {
            throw new InvalidPostException();
        }
        return postRepository.save(post);
    }

    public Optional<Post> deletePost(Integer postId){
        Optional<Post> postOpt = postRepository.findByPostId(postId);
        if(postOpt.isPresent()) {
            postRepository.delete(postOpt.get());
        }
        
        return postOpt;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Integer postId) {
        return postRepository.findByPostId(postId);
    }

    public void updatePost(Integer post_id, String postText) throws InvalidPostException {
        Optional<Post> postOpt = postRepository.findByPostId(post_id);
        if(postOpt.isPresent() && postText.length() < 255 && postText.length() > 0){
            Post post = postOpt.get();
            post.setPostText(postText);
            postRepository.save(post);
            postOpt = Optional.of(post);
        } else {
            throw new InvalidPostException();
        }
    }

    public List<Post> getPostsByUser(Integer accountId) {
        return postRepository.findByPostedBy(accountId);
    }
}
