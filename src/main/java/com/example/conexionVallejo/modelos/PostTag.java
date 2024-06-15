package com.example.conexionVallejo.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "posttag") // Asegúrate de que el nombre de la tabla coincida con tu base de datos

public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Se añadió un campo id, ya que es común tener una clave primaria en la tabla intermedia


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
