package com.example.conexionVallejo.servicios;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.repositorios.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> obtenerTodosLosPosts() {
        return postRepository.findAll();
    }
    

    public List<Post> obtenerTodosLosPostsAsc() {

        Pageable pageable = PageRequest.of(0, 15);  // Página 0, tamaño 15
        return postRepository.findAllByOrderByCreatedDateDesc(pageable);
    }

    // Método para obtener todos los posts que son preguntas
    public List<Post> obtenerPreguntas() {
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createdDate").descending());  // Página 0, tamaño 15, orden descendente por fecha de creación

        return postRepository.findAllByPostTypeId(1,pageable); // Suponiendo que el campo se llama "postType"
    }
    public List<Post> obtenerRespuestas(Long idPregunta) {
        // Obtener todas las respuestas relacionadas con la pregunta identificada por su ID
        return postRepository.findAllByParentQuestionId(idPregunta);
    }
    // Método para obtener todas las publicaciones paginadas
    public Page<Post> obtenerPostPaginados(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    // Método para obtener un post por su ID
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAnswersByParentQuestionId(Long parentQuestionId) {
        return postRepository.findAllByParentQuestionId(parentQuestionId);
    }
}
