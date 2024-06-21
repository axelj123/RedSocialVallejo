package com.example.conexionVallejo.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.conexionVallejo.modelos.User;

public interface UserRepository extends JpaRepository<User, Long> {

Optional<User> findByEmailAddress(String emailAddress);
boolean existsByEmailAddress(String emailAddress);
    Optional<User> findById(Long id);

}
