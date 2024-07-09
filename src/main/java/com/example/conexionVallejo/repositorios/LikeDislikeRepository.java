package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.conexionVallejo.modelos.LikeDislike;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeDislikeRepository extends JpaRepository<LikeDislike, Integer> {
    List<LikeDislike> findByPost(Post post);

    LikeDislike findByUserAndPost(User user, Post post);

    void deleteByPost(Post postId);


    long countByPostAndIsLikeIsTrue(Post post);

    long countByPostAndIsLikeIsFalse(Post post);


}

