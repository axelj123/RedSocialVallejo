package com.example.conexionVallejo.servicios;

import com.example.conexionVallejo.modelos.LikeDislike;
import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.LikeDislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeDislikeService {

    @Autowired
    private LikeDislikeRepository likeDislikeRepository;

    public LikeDislike likePost(User user, Post post) {
        LikeDislike likeDislike = new LikeDislike(user, post, true);
        return likeDislikeRepository.save(likeDislike);
    }

    public LikeDislike dislikePost(User user, Post post) {
        LikeDislike likeDislike = new LikeDislike(user, post, false);
        return likeDislikeRepository.save(likeDislike);
    }

    public long countLikesForPost(Post post) {
        return likeDislikeRepository.countByPostAndIsLikeIsTrue(post);
    }

    public long countDisLikesForPost(Post post) {
        return likeDislikeRepository.countByPostAndIsLikeIsFalse(post);
    }
}
