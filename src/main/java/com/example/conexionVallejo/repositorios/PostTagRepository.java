package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    @Transactional
    void deleteByPostId(Long postId);


}