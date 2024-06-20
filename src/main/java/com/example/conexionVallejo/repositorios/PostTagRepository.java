package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.PostTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    @Transactional
    void deleteByPostId(Long postId);


}