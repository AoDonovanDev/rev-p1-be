package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name="postlike")
public class PostLike {
    
    @Column (name="likeId")
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer likeId;

    @Column (name="accountId")
    private Integer accountId;

    @Column (name="postId")
    private Integer postId;

    public PostLike(){}
    
    public PostLike(Integer accountId, Integer postId){
        this.accountId = accountId;
        this.postId = postId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        PostLike other = (PostLike) obj;
        if(this.accountId != other.accountId)
            return false;
        if(this.postId != other.postId)
            return false;
        return true;
    }
}
