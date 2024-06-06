package com.example.conexionVallejo.modelos;


import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;

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

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_details")
    private String postDetails;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "posttag",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag> tag;


    public Post() {
        super();
    }


    public Post(Integer id, User createdByUser, Post parentQuestion, PostType postType, Post acceptedAnswer,
                String postTitle, String postDetails, Timestamp createdDate) {
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


    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
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


    public Timestamp getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }


}