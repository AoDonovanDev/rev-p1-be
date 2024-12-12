package com.example.service;


import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.entity.Account;
import com.example.entity.Post;
import com.example.entity.PostLike;
import com.example.repository.PostLikeRepository;
import com.example.repository.PostRepository;

@Service
@Transactional
public class PostLikeService {
    
    PostLikeRepository postLikeRepository;
    PostRepository postRepository;

    @Autowired
    public PostLikeService(PostRepository postRepository, PostLikeRepository postLikeRepository){
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
    }

    void addLike(Integer accountId, Integer postId){
        //TODO should validate with error handling for invalid posts and accounts
        PostLike newLike = new PostLike( accountId, postId);
        postLikeRepository.save(newLike);
    }

    void removeLike(Integer accountId, Integer postId){
        Optional<PostLike> plOpt = postLikeRepository.findByAccountIdAndPostId(accountId, postId);
        if(plOpt.isPresent()){
            PostLike pl = plOpt.get();
            postLikeRepository.delete(pl);
        }
    }

    List<PostLike> findPostsLikedByAccount(Integer accountId){
        List<PostLike> plList = postLikeRepository.findByAccountId(accountId);
        return plList;
    }

}
