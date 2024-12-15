package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Post;
import com.example.entity.PostLike;
import com.example.exception.InvalidPostException;
import com.example.repository.AccountRepository;
import com.example.repository.PostLikeRepository;
import com.example.repository.PostRepository;

@Service
@Transactional
public class PostService {

    PostRepository postRepository;

    AccountRepository accountRepository;

    PostLikeRepository postLikeRepository;

    @Autowired
    public PostService(PostRepository postRepository, AccountRepository accountRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.postLikeRepository = postLikeRepository;
    }

    public Post createPost(Post post) throws InvalidPostException{
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
        return accountRepository.findById(accountId).get().getPosts();
    }

    public void addLike(Integer accountId, Integer postId){
        PostLike pl = new PostLike(accountId, postId);
        postLikeRepository.save(pl);
    }

    public void removeLike(Integer accountId, Integer postId){
        PostLike pl = postLikeRepository.findByAccountIdAndPostId(accountId, postId).get();
        postLikeRepository.delete(pl);
    }
}
