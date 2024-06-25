package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.Post;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import com.example.conexionVallejo.modelos.Tag;
import com.example.conexionVallejo.modelos.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByPostTypeId(Integer postTypeId, Pageable pageable);


    List<Post> findAllByParentQuestionId(Long parentQuestionId);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.createdByUser = :user AND p.parentQuestion IS NULL")
    long countQuestionsByCreatedByUser(@Param("user") User user);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.createdByUser = :user AND p.parentQuestion IS NOT NULL")
    long countAnswersByCreatedByUser(@Param("user") User user);


    // Método para obtener posts de tipo 1 paginados
    @Query("SELECT p FROM Post p WHERE p.postType.id = 1")
    Page<Post> findAllByPostType1(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.createdByUser = :user AND p.postType.id = 1")
    List<Post> findByCreatedByUser(@Param("user") User user);

    @Query(value = "SELECT DISTINCT p.* " +
            "FROM Posts p " +  // Nombre de la tabla según tu configuración
            "LEFT JOIN posttag pt ON p.id = pt.post_id " +  // Nombre correcto de la tabla de relación
            "LEFT JOIN tags t ON pt.tag_id = t.id " +  // Nombre correcto de la tabla de etiquetas
            "WHERE LOWER(p.post_title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(t.tag_name) LIKE LOWER(CONCAT('%', :query, '%'))", nativeQuery = true)
    List<Post> searchPosts(@Param("query") String query);


    @Query("SELECT p FROM Post p WHERE p.postType.id = 1 AND p.id NOT IN (SELECT r.parentQuestion.id FROM Post r WHERE r.parentQuestion IS NOT NULL)")
    Page<Post> findUnansweredPosts(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.tag t WHERE t.tagName IN :tagNames")
    Page<Post> findByTags(@Param("tagNames") List<String> tagNames, Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* " +
            "FROM Posts p " +
            "JOIN posttag pt ON p.id = pt.post_id " +
            "JOIN Tags t ON pt.tag_id = t.id " +
            "WHERE p.post_type_id = 1 " +
            "AND p.parent_question_id IS NULL " +
            "AND NOT EXISTS ( " +
            "  SELECT 1 " +
            "  FROM Posts pq " +
            "  WHERE pq.parent_question_id = p.id " +
            ") " +
            "AND t.tag_name IN (:tagNames) " +
            "ORDER BY p.created_date DESC", // Asegúrate de usar el alias p para created_date
            nativeQuery = true)
    Page<Post> findUnansweredPostsByTags(@Param("tagNames") List<String> tagNames, Pageable pageable);

}
