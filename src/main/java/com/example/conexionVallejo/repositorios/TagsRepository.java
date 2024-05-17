package com.example.conexionVallejo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.conexionVallejo.modelos.Tag;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long>{

}
