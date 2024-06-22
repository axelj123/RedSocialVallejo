package com.example.conexionVallejo.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.conexionVallejo.modelos.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String emailAddress);

    Optional<User> findById(Long id);

    @Query("SELECT ur.role FROM UserRole ur WHERE ur.user.id = :userId")
    List<String> getUserRoles(@Param("userId") Long userId);
}
