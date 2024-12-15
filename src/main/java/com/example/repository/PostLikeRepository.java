package com.example.repository;
import com.example.entity.PostLike;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer>{
    
    public Optional<PostLike> findByAccountIdAndPostId(Integer accountId, Integer postId);
}
