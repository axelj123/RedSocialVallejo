package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.Report;
import com.example.conexionVallejo.modelos.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByUserAndPost(User user, Post post);
    @Transactional

    void deleteByPost(Post postId);

}
