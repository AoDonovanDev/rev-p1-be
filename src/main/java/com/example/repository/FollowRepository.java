package com.example.repository;
import java.util.List;
import com.example.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowRepository extends JpaRepository<Follow, Integer> {
    
    public List<Follow> findByFollowingAccountId(Integer followingAccountId);

    public List<Follow> findByFollowedAccountId(Integer followedAccountId);
}
