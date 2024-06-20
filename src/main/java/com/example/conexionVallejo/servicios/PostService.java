package com.example.conexionVallejo.servicios;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.repositorios.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;



    // Método para obtener todos los posts que son preguntas
    public List<Post> obtenerPreguntas() {
        Pageable pageable = PageRequest.of(0, 15, Sort.by("createdDate").descending());  // Página 0, tamaño 15, orden descendente por fecha de creación

        return postRepository.findAllByPostTypeId(1, pageable); // Suponiendo que el campo se llama "postType"
    }

    public List<Post> obtenerRespuestas(Long idPregunta) {
        // Obtener todas las respuestas relacionadas con la pregunta identificada por su ID
        return postRepository.findAllByParentQuestionId(idPregunta);
    }

    // Método para obtener posts de tipo 1 paginados
    public Page<Post> obtenerPostsTipo1Paginados(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, orderBy.equals("newest") ? Sort.by("createdDate").descending() : Sort.by("createdDate").ascending());
        return postRepository.findAllByPostType1(pageable);
    }

    // Método para obtener un post por su ID
    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }



    // Método para obtener posts sin respuesta paginados
    public Page<Post> obtenerPostsSinRespuestaPaginados(Integer page, Integer size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, orderBy.equals("newest") ? Sort.by("createdDate").descending() : Sort.by("createdDate").ascending());
        return postRepository.findUnansweredPosts(pageable);
    }

    public Page<Post> obtenerPostsPaginadosPorTags(Integer page, Integer size, List<String> tags, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, orderBy.equals("newest") ? Sort.by("createdDate").descending() : Sort.by("createdDate").ascending());
        return postRepository.findByTags(tags, pageable);
    }


    // Método para obtener publicaciones sin respuestas por etiquetas
    public Page<Post> obtenerPostsSinRespuestaPaginadosPorTags(int page, int size, List<String> tags, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, orderBy.equals("newest") ? Sort.by("createdDate").descending() : Sort.by("createdDate").ascending());
        return postRepository.findUnansweredPostsByTags(tags, pageable);
    }
}
