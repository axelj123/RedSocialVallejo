package com.example.conexionVallejo.controlador;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.servicios.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PostController {

    @Autowired
    private PostService postService;
 
    @GetMapping ("/posts")
    public List<Post> obtenerTodosLosPosts() {
    	
        return postService.obtenerTodosLosPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> obtenerPostPorId(@PathVariable Long id) {
        Optional<Post> post = postService.obtenerPostPorId(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Post> crearPost(@RequestBody Post post) {
        Post nuevoPost = postService.guardarPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPost(@PathVariable Long id) {
        postService.eliminarPost(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/preguntas")
    public List<Post> obtenerPreguntas() {
        return postService.obtenerPreguntas();
    }

}
