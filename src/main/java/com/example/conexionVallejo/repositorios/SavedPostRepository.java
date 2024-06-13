package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.SavedPost;
import com.example.conexionVallejo.modelos.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedPostRepository extends JpaRepository <SavedPost, Long> {
    Optional<SavedPost> findByUserAndPost(User user, Post post);
    List<SavedPost> findByUser(User user);

}
