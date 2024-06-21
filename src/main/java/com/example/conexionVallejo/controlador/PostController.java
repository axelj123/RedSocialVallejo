package com.example.conexionVallejo.controlador;

import java.sql.Timestamp;
import java.util.*;

import com.example.conexionVallejo.modelos.*;
import com.example.conexionVallejo.repositorios.*;
import com.example.conexionVallejo.servicios.SearchService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    private SavedPostRepository savedPostRepository;
    @Autowired
    private SearchService searchService;

    @Autowired
    private PostTagRepository postTagRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @DeleteMapping("/perfil/remove-saved-post/{savedPostId}")
    public ResponseEntity<Map<String, String>> deletePostSave(@PathVariable Long savedPostId, Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                response.put("message", "Usuario no autenticado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String loggedInUsername = authentication.getName();

            Optional<User> optionalUser = userRepository.findByEmailAddress(loggedInUsername);

            if (optionalUser.isEmpty()) {
                response.put("message", "Usuario no autenticado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            User user = optionalUser.get();

            Optional<SavedPost> savedPostOptional = savedPostRepository.findById(savedPostId);
            if (savedPostOptional.isEmpty()) {
                response.put("message", "SavedPost no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            SavedPost savedPostToDelete = savedPostOptional.get();

            if (!savedPostToDelete.getUser().equals(user)) {
                response.put("message", "No tienes permiso para eliminar este SavedPost");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            savedPostRepository.delete(savedPostToDelete);

            response.put("message", "Post guardado eliminado con éxito");
            return ResponseEntity.ok(response);

        } catch (EmptyResultDataAccessException e) {
            response.put("message", "SavedPost no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Error al eliminar el SavedPost: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping("/answers/delete/{id}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long id) {
        try {
            deleteAnswerById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    public void deleteAnswerById(Long id) {
        postRepository.deleteById(id);
    }


    @DeleteMapping("/posts/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            Optional<Post> postOptional = postRepository.findById(id);
            if (postOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Post postToDelete = postOptional.get();

            // Verificar si el post es una respuesta (postType.id = 2)
            if (postToDelete.getPostType().getId().equals(2)) {
                // Si es una respuesta, eliminarla directamente
                deleteSinglePost(postToDelete);
            } else {
                // Si es una pregunta, eliminarla y también sus respuestas asociadas
                deleteQuestionWithAnswers(postToDelete);
            }

            // Devuelve un estado de éxito sin redirección
            return ResponseEntity.ok().build();

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la publicación: " + e.getMessage());
        }
    }

    // Método para eliminar un solo post (respuesta)
    private void deleteSinglePost(Post post) {
        try {
            // Eliminar las dependencias del post en otras tablas
            notificationRepository.deleteByPost_Id(post.getId().longValue());
            savedPostRepository.deleteByPostId(post.getId().longValue());
            postTagRepository.deleteByPostId(post.getId().longValue());
            // Eliminar el post
            postRepository.deleteById(post.getId().longValue());
        } catch (Exception e) {
            // Logging detallado del error
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el post hijo: " + e.getMessage());
        }
    }

    // Método para eliminar una pregunta con todas sus respuestas
    private void deleteQuestionWithAnswers(Post question) {
        try {
            // Obtener todas las respuestas asociadas a la pregunta y eliminarlas
            List<Post> answersToDelete = postRepository.findAllByParentQuestionId(question.getId().longValue());
            for (Post answer : answersToDelete) {
                deleteSinglePost(answer); // Eliminar cada respuesta
            }

            notificationRepository.deleteByPost_Id(question.getId().longValue());
            savedPostRepository.deleteByPostId(question.getId().longValue());
            postTagRepository.deleteByPostId(question.getId().longValue());

            // Finalmente, eliminar la pregunta principal
            postRepository.deleteById(question.getId().longValue());
        } catch (Exception e) {
            // Logging detallado del error
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar la pregunta y sus respuestas: " + e.getMessage());
        }
    }

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

                    // Guardar la respuesta
                    postRepository.save(answer);

                    // Crear y guardar la notificación
                    createNotification(parentPost.getCreatedByUser(), parentPost, user, answer.getCreatedDate());

                    return "redirect:/postopen/" + parentQuestionId; // Redirigir a la página original de la pregunta
                }
            }
        }
        return "redirect:/login";
    }

    private void createNotification(User recipient, Post relatedPost, User responder,Timestamp answerDate) {
        Notification notification = new Notification();
        notification.setUser(recipient);
        notification.setPost(relatedPost);
        notification.setMessage(responder.getDisplayName() + " ha respondido a tu pregunta.");
        notification.setProfileImage(responder.getProfileImage());
        notification.setCreatedDate(answerDate);
        notification.setRead(false); // Notificación inicialmente no leída
        notificationRepository.save(notification);
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
    @ResponseBody
    public ResponseEntity<Map<String, String>> savePost(@RequestParam("postId") Long postId, Authentication authentication) {
        Map<String, String> response = new HashMap<>();

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
                        response.put("message", "La publicación ya está guardada");
                        response.put("type", "error");
                    } else {
                        // Guarda la publicación
                        SavedPost savedPost = new SavedPost();
                        savedPost.setUser(user);
                        savedPost.setPost(post);
                        savedPostRepository.save(savedPost);

                        response.put("message", "Se guardó correctamente en tu perfil");
                        response.put("type", "success");
                    }
                } else {
                    response.put("message", "No se encontró la publicación con el ID especificado");
                    response.put("type", "error");
                }
            } else {
                response.put("message", "Usuario no autenticado");
                response.put("type", "error");
            }
        } else {
            response.put("message", "Usuario no autenticado");
            response.put("type", "error");
        }

        return ResponseEntity.ok(response);


    }




}
