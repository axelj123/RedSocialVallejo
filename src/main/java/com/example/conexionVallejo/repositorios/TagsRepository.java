package com.example.conexionVallejo.repositorios;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.conexionVallejo.modelos.Tag;
import com.example.conexionVallejo.modelos.User;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long>{
    // En tu repositorio de tags (TagsRepository)
    List<Tag> findByPostId(Long postId);


    Tag findByTagName(String tagName);

    @Query("SELECT t FROM Tag t JOIN PostTag pt ON t.id = pt.tag.id JOIN Post p ON pt.post.id = p.id WHERE p.createdByUser = :user GROUP BY t ORDER BY COUNT(p) DESC")
    List<Tag> findTopTagsByUser(@Param("user") User user);

    List<Tag> findByTagNameIn(List<String> tagNames);

}
