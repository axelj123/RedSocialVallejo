package com.example.conexionVallejo.servicios;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.repositorios.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private PostRepository postRepository;


}