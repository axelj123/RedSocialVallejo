package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.Post;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByPostTypeId(Integer postTypeId,Pageable pageable);
    List<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);
    List<Post> findAllByParentQuestionId(Long parentQuestionId);

}
