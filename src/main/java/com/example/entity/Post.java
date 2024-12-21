package com.example.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="post")
public class Post {
   
     @Column (name="postId")
     @Id @GeneratedValue
    private Integer postId;
    
    @Column (name="postedBy")
    private Integer postedBy;
  
    @Column (name="postText")
    private String postText;
 
    @Column (name="timePostedEpoch")
    private Long timePostedEpoch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="postedBy", insertable = false, updatable = false)
    private Account account;
    
    @OneToMany(mappedBy = "cmPostId")
    private List<Comment> comments;

    @OneToMany(mappedBy = "plPostId")
    private List<PostLike> postLikes;

   
    public Post(){
    }
    /**
     * When posting a new post, the id can be generated by the database. In that case, a constructor without
     * postId is needed.
     * @param postedBy
     * @param postText
     * @param timePostedEpoch
     */
    public Post(Integer postedBy, String postText, Long timePostedEpoch) {
        this.postedBy = postedBy;
        this.postText = postText;
        this.timePostedEpoch = timePostedEpoch;
    }
    /**
     * Whem retrieving a post from the database, all fields will be needed. In that case, a constructor with all
     * fields is needed.
     * @param postId
     * @param postedBy
     * @param postText
     * @param timePostedEpoch
     */
    public Post(Integer postId, Integer postedBy, String postText, Long timePostedEpoch) {
        this.postId = postId;
        this.postedBy = postedBy;
        this.postText = postText;
        this.timePostedEpoch = timePostedEpoch;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return postId
     */
    public Integer getPostId() {
        return postId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param postId
     */
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return postedBy
     */
    public Integer getPostedBy() {
        return postedBy;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param postedBy
     */
    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return postText
     */
    public String getPostText() {
        return postText;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param postText
     */
    public void setPostText(String postText) {
        this.postText = postText;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return timePostedEpoch
     */
    public Long getTimePostedEpoch() {
        return timePostedEpoch;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param timePostedEpoch
     */
    public void setTimePostedEpoch(Long timePostedEpoch) {
        this.timePostedEpoch = timePostedEpoch;
    }

    public String getAccount(){
        String username = account.getUsername();
        return username;
    }

    public List<Comment> getComments(){
        return this.comments;
    }

    public List<Integer> getPostLikes() {
        List<Integer> pls = postLikes == null ? new ArrayList<>() : postLikes.stream().map(el -> el.getPlAccountId()).toList();
        return pls;
    }
    /**
     * Overriding the default equals() method adds functionality to tell when two objects are identical, allowing
     * Assert.assertEquals and List.contains to function.
     * @param o the other object.
     * @return true if o is equal to this object.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (postText == null) {
			if (other.postText != null)
				return false;
		} else if (!postText.equals(other.postText))
			return false;
		if (postedBy == null) {
			if (other.postedBy != null)
				return false;
		} else if (!postedBy.equals(other.postedBy))
			return false;
		if (timePostedEpoch == null) {
			if (other.timePostedEpoch != null)
				return false;
		} else if (!timePostedEpoch.equals(other.timePostedEpoch))
			return false;
		return true;
	}
	
    /**
     * Overriding the default toString() method allows for easy debugging.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postedBy=" + postedBy +
                ", postText='" + postText + '\'' +
                ", timePostedEpoch=" + timePostedEpoch +
                '}';
    }


}
