package com.example.conexionVallejo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.conexionVallejo.modelos.PostTag;


@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer>{

}
