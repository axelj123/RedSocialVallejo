package com.example.conexionVallejo.servicios;

import com.example.conexionVallejo.modelos.LikeDislike;
import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.LikeDislikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeDislikeService {

    @Autowired
    private LikeDislikeRepository likeDislikeRepository;

    @Transactional
    public LikeDislike toggleLike(User user, Post post) {
        LikeDislike existing = likeDislikeRepository.findByUserAndPost(user, post);
        if (existing != null) {
            if (Boolean.TRUE.equals(existing.getIsLike())) {
                likeDislikeRepository.delete(existing);
                return null;
            } else {
                existing.setIsLike(true);
                return likeDislikeRepository.save(existing);
            }
        } else {
            LikeDislike likeDislike = new LikeDislike(user, post, true);
            return likeDislikeRepository.save(likeDislike);
        }
    }

    @Transactional
    public LikeDislike toggleDislike(User user, Post post) {
        LikeDislike existing = likeDislikeRepository.findByUserAndPost(user, post);
        if (existing != null) {
            if (Boolean.FALSE.equals(existing.getIsLike())) {
                likeDislikeRepository.delete(existing);
                return null;
            } else {
                existing.setIsLike(false);
                return likeDislikeRepository.save(existing);
            }
        } else {
            LikeDislike likeDislike = new LikeDislike(user, post, false);
            return likeDislikeRepository.save(likeDislike);
        }
    }


    public long countLikesForPost(Post post) {
        return likeDislikeRepository.countByPostAndIsLikeIsTrue(post);
    }

    public long countDisLikesForPost(Post post) {
        return likeDislikeRepository.countByPostAndIsLikeIsFalse(post);
    }
    public LikeDislike findByUserAndPost(User user, Post post) {
        return likeDislikeRepository.findByUserAndPost(user, post);
    }
}
