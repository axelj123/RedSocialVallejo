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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    @ManyToOne
    @JoinColumn(name = "parent_question_user_id")
    private User parentQuestionUser;

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
    private Date createdDate;

    // Getters and setters
}