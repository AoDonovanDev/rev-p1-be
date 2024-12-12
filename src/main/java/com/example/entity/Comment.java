package com.example.entity;
import javax.persistence.*;

@Entity
@Table(name="comment")
public class Comment {
    
    @Column (name="commentId")
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentId;

    @Column (name="postId")
    private Integer postId;

    @Column (name="commentedBy")
    private Integer commentedBy;

    @Column (name="commentText")
    private String commentText;

    public Comment(){}

    public Comment(Integer postId, Integer commentedBy, String commentText){
        this.postId = postId;
        this.commentedBy = commentedBy;
        this.commentText = commentText;
    }

    public Comment(Integer commentId, Integer postId, Integer commentedBy, String commentText){
        this.commentId = commentId;
        this.postId = postId;
        this.commentedBy = commentedBy;
        this.commentText = commentText;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getCommentedBy() {
        return commentedBy;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setCommentedBy(Integer commentedBy) {
        this.commentedBy = commentedBy;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (postId == null) {
			if (other.commentId != null)
				return false;
		} else if (!postId.equals(other.commentId))
			return false;
		if (commentText == null) {
			if (other.commentText != null)
				return false;
		} else if (!commentText.equals(other.commentText))
			return false;
		if (commentedBy == null) {
			if (other.commentedBy != null)
				return false;
		} else if (!commentedBy.equals(other.commentedBy))
			return false;
		return true;
    }
}
