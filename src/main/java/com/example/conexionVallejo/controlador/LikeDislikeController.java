package com.example.conexionVallejo.controlador;

import com.example.conexionVallejo.modelos.LikeDislike;
import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.servicios.LikeDislikeService;
import com.example.conexionVallejo.servicios.PostService;
import com.example.conexionVallejo.servicios.UserServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeDislikeController {

    @Autowired
    private LikeDislikeService likeDislikeService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserServicios userServicios;

    @PostMapping("/like/{postId}")
    public ResponseEntity<LikeDislike> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        Post post = postService.postByID(postId);
        User user = userServicios.findById(userId);
        LikeDislike likeDislike = likeDislikeService.likePost(user, post);
        return ResponseEntity.ok(likeDislike);
    }

    @PostMapping("/dislike/{postId}")
    public ResponseEntity<LikeDislike> dislikePost(@PathVariable Long postId, @RequestParam Long userId) {
        Post post = postService.postByID(postId);
        User user = userServicios.findById(userId);
        LikeDislike likeDislike = likeDislikeService.dislikePost(user, post);
        return ResponseEntity.ok(likeDislike);
    }

    // Método GET para obtener contadores de likes y dislikes
    @GetMapping("/api/likeDislike/{postId}")
    public ResponseEntity<Map<String, Long>> getLikeDislikeCounts(@PathVariable Long postId) {
        Post post = postService.postByID(postId);

        // Obtener contadores actualizados
        long likeCount = likeDislikeService.countLikesForPost(post);
        long dislikeCount = likeDislikeService.countDisLikesForPost(post);

        Map<String, Long> response = new HashMap<>();
        response.put("likes", likeCount);
        response.put("dislikes", dislikeCount);

        return ResponseEntity.ok(response);
    }

    // Otros métodos de controlador
}