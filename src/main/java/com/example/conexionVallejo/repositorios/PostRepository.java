package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.Post;

import java.util.List;

import com.example.conexionVallejo.modelos.Tag;
import com.example.conexionVallejo.modelos.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByPostTypeId(Integer postTypeId,Pageable pageable);
    List<Post> findAllByOrderByCreatedDateDesc(Pageable pageable);
    List<Post> findAllByParentQuestionId(Long parentQuestionId);
    void deleteByParentQuestionId(Long parentQuestionId);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.createdByUser = :user AND p.parentQuestion IS NULL")
    long countQuestionsByCreatedByUser(@Param("user") User user);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.createdByUser = :user AND p.parentQuestion IS NOT NULL")
    long countAnswersByCreatedByUser(@Param("user") User user);


    @Query("SELECT p FROM Post p WHERE p.createdByUser = :user AND p.postType.id = 1")
    List<Post> findByCreatedByUser(@Param("user") User user);
    @Query(value = "SELECT DISTINCT p.* " +
            "FROM Posts p " +  // Nombre de la tabla según tu configuración
            "LEFT JOIN posttag pt ON p.id = pt.post_id " +  // Nombre correcto de la tabla de relación
            "LEFT JOIN tags t ON pt.tag_id = t.id " +  // Nombre correcto de la tabla de etiquetas
            "WHERE LOWER(p.post_title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(t.tag_name) LIKE LOWER(CONCAT('%', :query, '%'))", nativeQuery = true)
    List<Post> searchPosts(@Param("query") String query);
}
