package com.example.conexionVallejo.controlador;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.conexionVallejo.modelos.*;
import com.example.conexionVallejo.repositorios.*;
import com.example.conexionVallejo.servicios.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostTypeRepository postTypeRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private FileService fileService;
@Autowired
private SavedPostRepository savedPostRepository;

    @PostMapping("/post/new")
    public String submitNewPost(@ModelAttribute Post post,
                                @RequestParam("tags") String[] tagIds
                              ,
                                Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                post.setCreatedByUser(user);

                Optional<PostType> optionalPostType = postTypeRepository.findById(1L);
                if (optionalPostType.isPresent()) {
                    PostType postType = optionalPostType.get();
                    post.setPostType(postType);
                }

                post.setCreatedDate(new Timestamp(System.currentTimeMillis()));

                List<Tag> tags = new ArrayList<>();
                for (String tagName : tagIds) {
                    Tag tag = tagsRepository.findByTagName(tagName);
                    if (tag != null) {
                        tags.add(tag);
                    }
                }
                post.setTag(tags);

                postRepository.save(post);


                return "redirect:/foro";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/answer/new")
    public String submitAnswer(@RequestParam("parentQuestionId") Long parentQuestionId,
                               @RequestParam("postDetails") String postDetails,
                               Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                Post answer = new Post();
                answer.setCreatedByUser(user);
                answer.setPostDetails(postDetails);
                answer.setCreatedDate(new Timestamp(System.currentTimeMillis()));

                Optional<PostType> optionalPostType = postTypeRepository.findById(2L); // Assuming 2 is the PostType ID for answers
                if (optionalPostType.isPresent()) {
                    PostType postType = optionalPostType.get();
                    answer.setPostType(postType);
                }

                Optional<Post> optionalParentPost = postRepository.findById(parentQuestionId);
                if (optionalParentPost.isPresent()) {
                    Post parentPost = optionalParentPost.get();
                    answer.setParentQuestion(parentPost);
                }

                postRepository.save(answer);

                return "redirect:/postopen/" + parentQuestionId; // Redirect to the original post page
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/post/edit")
    public String editPost(@RequestParam("postId") Long postId,
                           @RequestParam("postTitle") String postTitle,
                           @RequestParam("postDetails") String postDetails,
                           @RequestParam("tags") String[] tagIds,
                           Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<Post> optionalPost = postRepository.findById(postId);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                String emailAddress = authentication.getName();
                Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
                if (optionalUser.isPresent() && post.getCreatedByUser().getId().equals(optionalUser.get().getId())) {
                    post.setPostTitle(postTitle);
                    post.setPostDetails(postDetails);

                    List<Tag> tags = new ArrayList<>();
                    for (String tagName : tagIds) {
                        Tag tag = tagsRepository.findByTagName(tagName);
                        if (tag != null) {
                            tags.add(tag);
                        }
                    }
                    post.setTag(tags);

                    postRepository.save(post);

                    return "redirect:/postopen/" + postId; // Redirect to the edited post page
                }
            }
        }
        return "redirect:/login";
    }
    @PostMapping("/post/delete")
    public String deletePost(@RequestParam("postId") Long postId, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<Post> optionalPost = postRepository.findById(postId);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                String emailAddress = authentication.getName();
                Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
                if (optionalUser.isPresent() && post.getCreatedByUser().getId().equals(optionalUser.get().getId())) {
                    // Buscar todas las respuestas asociadas a la publicación
                    List<Post> respuestas = postRepository.findAllByParentQuestionId(postId);
                    for (Post respuesta : respuestas) {
                        // Eliminar las etiquetas asociadas a la respuesta
                        respuesta.getTag().clear();
                    }
                    // Eliminar todas las respuestas
                    postRepository.deleteAll(respuestas);

                    // Eliminar las etiquetas asociadas a la publicación
                    post.getTag().clear();

                    // Eliminar la publicación
                    postRepository.delete(post);

                    return "redirect:/foro";
                }
            }
        }
        return "redirect:/login";
    }
    @PostMapping("/answer/edit")
    public String editAnswer(@RequestParam("postId") Long answerId,
                             @RequestParam("postDetails") String postDetails,
                             Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Optional<Post> optionalAnswer = postRepository.findById(answerId);
            if (optionalAnswer.isPresent()) {
                Post answer = optionalAnswer.get();
                String emailAddress = authentication.getName();
                Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
                if (optionalUser.isPresent() && answer.getCreatedByUser().getId().equals(optionalUser.get().getId())) {
                    answer.setPostDetails(postDetails);

                    postRepository.save(answer);

                    // Redirige a la página de la publicación original
                    return "redirect:/postopen/" + answer.getParentQuestion().getId();
                }
            }
        }
        // Redirige al inicio de sesión si no está autenticado o no es el autor de la respuesta
        return "redirect:/login";
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePost(@RequestParam("postId") Long postId, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Optional<Post> optionalPost = postRepository.findById(postId);
                if (optionalPost.isPresent()) {
                    Post post = optionalPost.get();

                    // Verifica si la publicación ya está guardada por el usuario
                    Optional<SavedPost> existingSavedPost = savedPostRepository.findByUserAndPost(user, post);
                    if (existingSavedPost.isPresent()) {
                        return ResponseEntity.badRequest().body("La publicación ya está guardada");
                    }

                    // Guarda la publicación
                    SavedPost savedPost = new SavedPost();
                    savedPost.setUser(user);
                    savedPost.setPost(post);
                    savedPostRepository.save(savedPost);

                    return ResponseEntity.ok("Publicación guardada exitosamente");
                } else {
                    return ResponseEntity.badRequest().body("No se encontró la publicación con el ID especificado");
                }
            } else {
                return ResponseEntity.badRequest().body("Usuario no autenticado");
            }
        } else {
            return ResponseEntity.badRequest().body("Usuario no autenticado");
        }
    }

}
