package com.example.conexionVallejo.controlador;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @DeleteMapping("/removeSavedPost/{savedPostId}")
    public String removeSavedPost(@PathVariable Long savedPostId, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Optional<SavedPost> optionalSavedPost = savedPostRepository.findById(savedPostId);
                if (optionalSavedPost.isPresent()) {
                    SavedPost savedPost = optionalSavedPost.get();
                    if (savedPost.getUser().getId().equals(user.getId())) {
                        savedPostRepository.delete(savedPost);
                        return "redirect:/perfil?tab=guardados"; // Redirigir al perfil del usuario donde se muestran los post guardados
                    }
                }
            }
        }
        return "redirect:/login"; // Redirigir al inicio de sesión si no se pudo eliminar el post guardado
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
    @PostMapping("/postopen/{postId}")
    public String showPostDetails(@PathVariable Long postId, Model model, Authentication authentication) {
        // Obtener el post
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            // Verificar si el post está guardado por el usuario actual
            boolean isPostSaved = false;
            if (authentication != null && authentication.isAuthenticated()) {
                String emailAddress = authentication.getName();
                Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    Optional<SavedPost> existingSavedPost = savedPostRepository.findByUserAndPost(user, post);
                    isPostSaved = existingSavedPost.isPresent();
                }
            }
            model.addAttribute("isPostSaved", isPostSaved);
        }

return "";
    }




}
