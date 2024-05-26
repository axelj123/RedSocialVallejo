package com.example.conexionVallejo.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.conexionVallejo.modelos.Tag;
import com.example.conexionVallejo.modelos.User;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long>{
	

    Tag findByTagName(String tagName);

}
