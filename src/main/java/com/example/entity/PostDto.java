package com.example.entity;

public class PostDto {
    private Integer postId;
    private String username;
    private String postText;
    private Long timePostedEpoch;
    
    public PostDto(Integer postId, String username, String postText, Long timePostedEpoch) {
        this.postId = postId;
        this.username = username;
        this.postText = postText;
        this.timePostedEpoch = timePostedEpoch;
    }
    public Integer getPostId() {
        return postId;
    }
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPostText() {
        return postText;
    }
    public void setPostText(String postText) {
        this.postText = postText;
    }
    public Long getTimePostedEpoch() {
        return timePostedEpoch;
    }
    public void setTimePostedEpoch(Long timePostedEpoch) {
        this.timePostedEpoch = timePostedEpoch;
    }
}
