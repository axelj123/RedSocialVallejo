package com.example.conexionVallejo.controlador;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.conexionVallejo.modelos.SavedPost;
import com.example.conexionVallejo.repositorios.PostRepository;
import com.example.conexionVallejo.repositorios.SavedPostRepository;
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
    private SavedPostRepository savedPostRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private PostRepository postRepository;
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

                    // Obtener la fecha y hora actual en formato UTC
                    Instant currentInstant = Instant.now();

                    // Convertir la fecha de creación del post a UTC
                    Instant postInstant = post.getCreatedDate().toInstant();

                    // Calcular la diferencia de tiempo entre ambas fechas
                    Duration duration = Duration.between(postInstant, currentInstant);

                    // Obtener la antigüedad del post en minutos, horas, días o semanas
                    String age = calculateAge(duration);

                    // Pasar la antigüedad del post al modelo
                    model.addAttribute("age", age);

                    // Cargar las respuestas relacionadas
                    List<Post> answers = postService.obtenerRespuestas(id);

                    // Calcular la antigüedad de cada respuesta y agregarla al modelo
                    Map<Integer, String> answerAges = new HashMap<>();
                    for (Post answer : answers) {
                        Instant answerInstant = answer.getCreatedDate().toInstant();
                        Duration answerDuration = Duration.between(answerInstant, currentInstant);
                        String answerAge = calculateAge(answerDuration);
                        answerAges.put(answer.getId(), answerAge);
                    }

                    model.addAttribute("answerAges", answerAges);
                    model.addAttribute("answers", answers);


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


    private String calculateAge(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();

        if (days >= 7) {
            long weeks = days / 7;
            return "hace " + weeks + (weeks == 1 ? " semana" : " semanas");
        } else if (days >= 1) {
            return "hace " + days + (days == 1 ? " día" : " días");
        } else if (hours >= 1) {
            return "hace " + hours + (hours == 1 ? " hora" : " horas");
        } else {
            return "hace " + minutes + (minutes == 1 ? " minuto" : " minutos");
        }
    }
    @GetMapping("/search")
    public String search(@RequestParam("q") String query, Model model) {
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

        // Aquí se llama al método searchPosts del repositorio para obtener los resultados de búsqueda
        List<Post> posts = postRepository.searchPosts(query);

        // Obtener la fecha y hora actual en formato UTC
        Instant currentInstant = Instant.now();

        // Calcular la antigüedad de cada post y agregarla al modelo
        Map<Integer, String> postAges = new HashMap<>();
        for (Post post : posts) {
            Instant postInstant = post.getCreatedDate().toInstant();
            Duration duration = Duration.between(postInstant, currentInstant);
            String age = calculateAge(duration);
            postAges.put(post.getId(), age);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("postAges", postAges);
        return "search"; // Nombre de la plantilla Thymeleaf para mostrar los resultados de búsqueda
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

        List<Post> posts = postService.obtenerPreguntas();

        // Obtener la fecha y hora actual en formato UTC
        Instant currentInstant = Instant.now();

        // Calcular la antigüedad de cada post y agregarla al modelo
        Map<Integer, String> postAges = new HashMap<>();
        for (Post post : posts) {
            Instant postInstant = post.getCreatedDate().toInstant();
            Duration duration = Duration.between(postInstant, currentInstant);
            String age = calculateAge(duration);
            postAges.put(post.getId(), age);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("postAges", postAges);
        return "foro";
    }


    @GetMapping("/posts/{id}/edit")
    public String postEdit(@PathVariable Long id, Model model) {
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

                    return "editPost";
                } else {
                    model.addAttribute("errorMessage", "Post no encontrado");
                    return "error";
                }
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/answer/{id}/edit")
    public String answerEdit(@PathVariable Long id, Model model) {
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
                    model.addAttribute("post", post); // Aquí debería ser "answer" en lugar de "post"

                    return "editAnswer";
                } else {
                    model.addAttribute("errorMessage", "Post no encontrado");
                    return "error";
                }
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping("/login")
    public String login() {
        return "loginregister";
    }

    @GetMapping("/perfil")
    public String perfil(@RequestParam(value = "tab", defaultValue = "info") String tab, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                model.addAttribute("user", user);

                if ("info".equals(tab)) {
                    // Obtener número de respuestas y preguntas del usuario
                    long numPreguntas = postRepository.countQuestionsByCreatedByUser(user);
                    long numRespuestas = postRepository.countAnswersByCreatedByUser(user);


                    model.addAttribute("numRespuestas", numRespuestas);
                    model.addAttribute("numPreguntas", numPreguntas);

                    // Obtener las etiquetas más utilizadas por el usuario
                    List<Tag> topTags = tagsRepository.findTopTagsByUser(user);
                    model.addAttribute("topTags", topTags);

                    // Obtener todas las publicaciones y respuestas del usuario
                    List<Post> userPosts = postRepository.findByCreatedByUser(user);
                    model.addAttribute("userPosts", userPosts);

                    // Si la pestaña activa es "guardados", carga las publicaciones guardadas
                } else if ("guardados".equals(tab)) {
                    List<SavedPost> savedPosts = savedPostRepository.findByUser(user);

                    // Obtener la fecha y hora actual en formato UTC
                    Instant currentInstant = Instant.now();

                    // Calcular la antigüedad de cada publicación guardada y agregarla al modelo
                    Map<Long, String> savedPostAges = new HashMap<>();
                    for (SavedPost savedPost : savedPosts) {
                        Instant postInstant = savedPost.getPost().getCreatedDate().toInstant();
                        Duration duration = Duration.between(postInstant, currentInstant);
                        String age = calculateAge(duration);
                        savedPostAges.put(savedPost.getId(), age);
                    }

                    model.addAttribute("savedPosts", savedPosts);
                    model.addAttribute("savedPostAges", savedPostAges);
                }
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

    @GetMapping("/users/{userId}")
    public String viewUserProfile(@PathVariable Long userId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User loggedInUser = optionalUser.get();
                model.addAttribute("user", loggedInUser);

                Optional<User> optionalProfileUser = userRepository.findById(userId);
                if (optionalProfileUser.isPresent()) {
                    User profileUser = optionalProfileUser.get();
                    model.addAttribute("profileUser", profileUser);

                    // Verificar si el perfil es del usuario autenticado
                    boolean isCurrentUser = loggedInUser.getId().equals(profileUser.getId());
                    model.addAttribute("isCurrentUser", isCurrentUser);

                    if (!isCurrentUser) {
                        // Cargar las publicaciones guardadas del perfil seleccionado
                        List<SavedPost> savedPosts = savedPostRepository.findByUser(profileUser);

                        // Obtener la fecha y hora actual en formato UTC
                        Instant currentInstant = Instant.now();

                        // Calcular la antigüedad de cada publicación guardada y agregarla al modelo
                        Map<Long, String> savedPostAges = new HashMap<>();
                        for (SavedPost savedPost : savedPosts) {
                            Instant postInstant = savedPost.getPost().getCreatedDate().toInstant();
                            Duration duration = Duration.between(postInstant, currentInstant);
                            String age = calculateAge(duration);
                            savedPostAges.put(savedPost.getId(), age);
                        }

                        model.addAttribute("savedPosts", savedPosts);
                        model.addAttribute("savedPostAges", savedPostAges);
                    }

                    return "perfil"; // Nombre del archivo HTML de la vista del perfil
                } else {
                    return "redirect:/error"; // Manejar el caso en que el usuario no existe
                }
            } else {
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
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
    public String showLoginAdmin() {
        return "adminLogin";
    }

    @GetMapping("/ControlPanel")
    public String PanelDeControl() {
        return "panelControl";
    }

}
