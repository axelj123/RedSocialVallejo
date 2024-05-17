package com.example.conexionVallejo.modelos;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Posts")
public class Post {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @ManyToOne
	    @JoinColumn(name = "created_by_user_id")
	    private User createdByUser;

	    @ManyToOne
	    @JoinColumn(name = "parent_question_id")
	    private Post parentQuestion;

	    @ManyToOne
	    @JoinColumn(name = "post_type_id")
	    private PostType postType;

	    @OneToOne
	    @JoinColumn(name = "accepted_answer_id")
	    private Post acceptedAnswer;

	    private String postTitle;

	    private String postDetails;

	    private Date createdDate;

	    
	    
		public Post() {
			super();
		}



		public Post(Integer id, User createdByUser, Post parentQuestion, PostType postType, Post acceptedAnswer,
				String postTitle, String postDetails, Date createdDate) {
			super();
			this.id = id;
			this.createdByUser = createdByUser;
			this.parentQuestion = parentQuestion;
			this.postType = postType;
			this.acceptedAnswer = acceptedAnswer;
			this.postTitle = postTitle;
			this.postDetails = postDetails;
			this.createdDate = createdDate;
		}



		public Integer getId() {
			return id;
		}



		public void setId(Integer id) {
			this.id = id;
		}



		public User getCreatedByUser() {
			return createdByUser;
		}



		public void setCreatedByUser(User createdByUser) {
			this.createdByUser = createdByUser;
		}



		public Post getParentQuestion() {
			return parentQuestion;
		}



		public void setParentQuestion(Post parentQuestion) {
			this.parentQuestion = parentQuestion;
		}



		public PostType getPostType() {
			return postType;
		}



		public void setPostType(PostType postType) {
			this.postType = postType;
		}



		public Post getAcceptedAnswer() {
			return acceptedAnswer;
		}



		public void setAcceptedAnswer(Post acceptedAnswer) {
			this.acceptedAnswer = acceptedAnswer;
		}



		public String getPostTitle() {
			return postTitle;
		}



		public void setPostTitle(String postTitle) {
			this.postTitle = postTitle;
		}



		public String getPostDetails() {
			return postDetails;
		}



		public void setPostDetails(String postDetails) {
			this.postDetails = postDetails;
		}



		public Date getCreatedDate() {
			return createdDate;
		}



		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}


}