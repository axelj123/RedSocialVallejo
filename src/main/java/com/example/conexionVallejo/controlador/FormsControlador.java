package com.example.conexionVallejo.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.Tag;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.TagsRepository;
import com.example.conexionVallejo.repositorios.UserRepository;
import com.example.conexionVallejo.servicios.EmailRequest;
import com.example.conexionVallejo.servicios.PostService;

@Controller
public class FormsControlador {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TagsRepository tagsRepository;

    private final PostService postService;

    public FormsControlador(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/")
    public String inicio() {
        return "index";
    }


    @GetMapping("/postopen/{id}")
    public String postopen(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);

                Optional<Post> optionalPost = postService.findPostById(id);
                if (optionalPost.isPresent()) {
                    Post post = optionalPost.get();
                    model.addAttribute("post", post);
                    return "postopen"; // Nombre de la plantilla Thymeleaf
                } else {
                    model.addAttribute("errorMessage", "Post no encontrado");
                    return "error"; // Página de error específica
                }
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

    }

    @GetMapping("/foro")
    public String foro(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

        List<Post> posts = postService.obtenerTodosLosPostsAsc();
        model.addAttribute("posts", posts);
        return "foro";
    }


    @GetMapping("/login")
    public String login() {
        return "loginregister";
    }

    @GetMapping("/perfil")
    public String perfil(@RequestParam(value = "tab", defaultValue = "info") String tab, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

        // Se establece activeTab en función del parámetro tab en la URL
        model.addAttribute("activeTab", tab);

        return "Perfil";
    }

    @GetMapping("/users")
    public String users(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

        // Obtener todos los usuarios de la base de datos
        List<User> userList = userRepository.findAll();
        // Agregar la lista de usuarios al modelo
        model.addAttribute("userList", userList);

        return "users";
    }

    @GetMapping("/tags")
    public String Tags(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

        // Obtener todos los usuarios de la base de datos
        List<Tag> userList = tagsRepository.findAll();
        // Agregar la lista de usuarios al modelo
        model.addAttribute("userList", userList);

        return "tags";
    }

    @GetMapping("/preguntas")
    public String preguntas(Model model, @RequestParam(defaultValue = "0", required = false) Integer page, @RequestParam(defaultValue = "15", required = false) Integer size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postService.obtenerPostPaginados(pageable);
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());

        return "preguntas";
    }


    @GetMapping("/createPost")
    public String createPost(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }

        model.addAttribute("post", new Post());
        return "createpost";
    }

    @GetMapping("/recuperarPassword")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("emailRequest", new EmailRequest());
        return "forgotPasswordEmail";
    }

    @GetMapping("/nuevoPass")
    public String showNewPasswordPage(Model model) {
        model.addAttribute("emailRequest", new EmailRequest());
        return "nuevoPass";
    }

    @GetMapping("/adminLogin")
    public String showLoginAdmin(){
        return "adminLogin";
    }


}
