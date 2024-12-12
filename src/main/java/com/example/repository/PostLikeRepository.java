package com.example.repository;
import java.util.List;
import java.util.Optional;

import com.example.entity.PostLike;
import com.example.entity.Post;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer>{

    List<PostLike> findByAccountId(Integer accountId);

    List<PostLike> findByPostId(Integer postId);

    Optional<PostLike> findByAccountIdAndPostId(Integer accountId, Integer postId);
}