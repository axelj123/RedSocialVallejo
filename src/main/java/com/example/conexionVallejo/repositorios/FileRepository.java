package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}
