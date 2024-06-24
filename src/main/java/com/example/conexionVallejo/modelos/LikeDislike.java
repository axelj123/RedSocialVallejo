package com.example.conexionVallejo.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "likes_dislikes")
public class LikeDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "is_like")
    private Boolean isLike;

    // Constructor por defecto
    public LikeDislike() {
        super();
    }

    // Constructor con parámetros
    public LikeDislike(User user, Post post, Boolean isLike) {
        super();
        this.user = user;
        this.post = post;
        this.isLike = isLike;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }
}
