package com.example.conexionVallejo.controlador;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.example.conexionVallejo.modelos.SavedPost;
import com.example.conexionVallejo.repositorios.PostRepository;
import com.example.conexionVallejo.repositorios.SavedPostRepository;
import com.example.conexionVallejo.servicios.AgeCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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


                    // Convertir la fecha de creación del post a UTC
                    Instant postInstant = post.getCreatedDate().toInstant();

                    // Calcular la antigüedad del post principal
                    String postAge = AgeCalculatorService.calculatePostAge(postInstant);
                    model.addAttribute("age", postAge);

                    // Cargar las respuestas relacionadas
                    List<Post> answers = postService.obtenerRespuestas(id);

                    // Calcular la antigüedad de cada respuesta y agregarla al modelo
                    Map<Integer, String> answerAges = new HashMap<>();
                    for (Post answer : answers) {
                        Instant answerInstant = answer.getCreatedDate().toInstant();
                        String answerAge = AgeCalculatorService.calculatePostAge(answerInstant);
                        answerAges.put(answer.getId().intValue(), answerAge);
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

        // Calcular la antigüedad de cada post y agregarla al modelo
        Map<Integer, String> postAges = posts.stream()
                .collect(Collectors.toMap(
                        Post::getId,
                        post -> AgeCalculatorService.calculatePostAge(post.getCreatedDate().toInstant())
                ));
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

        // Calcular la antigüedad de cada post y agregarla al modelo
        Map<Integer, String> postAges = posts.stream()
                .collect(Collectors.toMap(
                        Post::getId,
                        post -> AgeCalculatorService.calculatePostAge(post.getCreatedDate().toInstant())
                ));


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



                    // Calcular la antigüedad de cada publicación guardada y agregarla al modelo
                    Map<Long, String> savedPostAges = new HashMap<>();
                    for (SavedPost savedPost : savedPosts) {
                        Instant postInstant = savedPost.getPost().getCreatedDate().toInstant();
                        String age = AgeCalculatorService.calculatePostAge(postInstant);
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
                            String age = AgeCalculatorService.calculatePostAge(postInstant);
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
    public String preguntas(Model model,
                            @RequestParam(defaultValue = "0", required = false) Integer page,
                            @RequestParam(defaultValue = "15", required = false) Integer size,
                            @RequestParam(required = false) Boolean unanswered,
                            @RequestParam(required = false) List<String> tags,
                            @RequestParam(defaultValue = "newest", required = false) String orderBy) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return "redirect:/login"; // Redireccionar si no hay autenticación
            }

            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (!optionalUser.isPresent()) {
                return "redirect:/login"; // Redireccionar si el usuario no está presente
            }
            User user = optionalUser.get();
            model.addAttribute("user", user);

            Page<Post> postsPage;
            Pageable pageable = PageRequest.of(page, size, orderBy.equals("newest") ? Sort.by("createdDate").descending() : Sort.by("createdDate").ascending());

            if (unanswered != null && unanswered) {
                if (tags != null && !tags.isEmpty()) {
                    // Filtrar por tags y preguntas sin respuesta
                    postsPage = postService.obtenerPostsSinRespuestaPaginadosPorTags(page, size, tags,orderBy);

                } else {
                    // Filtrar solo por preguntas sin respuesta
                    postsPage = postService.obtenerPostsSinRespuestaPaginados(page, size,orderBy);
                }
            } else if (tags != null && !tags.isEmpty()) {
                // Filtrar por tags
                postsPage = postService.obtenerPostsPaginadosPorTags(page, size, tags,orderBy);
            } else {
                // No hay filtros seleccionados
                postsPage = postService.obtenerPostsTipo1Paginados(page, size, orderBy);
            }

            // Verificar si no hay resultados y agregar un mensaje de advertencia
            if (postsPage.isEmpty()) {
                model.addAttribute("noResultsMessage", "No se encontraron publicaciones con los filtros seleccionados.");
            } else {
                Map<Integer, String> postAges = postsPage.stream()
                        .collect(Collectors.toMap(
                                Post::getId,
                                post -> AgeCalculatorService.calculatePostAge(post.getCreatedDate().toInstant())
                        ));
                model.addAttribute("postAges", postAges);
            }

            model.addAttribute("postsPage", postsPage);
            model.addAttribute("currentPage", page); // Usar 'page' en lugar de 'postsPage.getNumber()'
            model.addAttribute("totalPages", postsPage.getTotalPages());
            model.addAttribute("totalQuestions", postsPage.getTotalElements());
            model.addAttribute("unanswered", unanswered); // Pasar el filtro como atributo
            model.addAttribute("selectedTags", tags); // Pasar las etiquetas seleccionadas como atributo
            model.addAttribute("orderBy", orderBy); // Pasar el orden seleccionado como atributo

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Hubo un error al cargar las preguntas. Por favor, intenta nuevamente más tarde.");
        }

        return "preguntas";
    }




//    @GetMapping("/api/preguntas")
//    public String getPreguntasFiltradas(Model model,
//                                        @RequestParam(required = false) Boolean unanswered) {
//
//        try {
//            // Obtener la información del usuario autenticado si es necesario
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return "redirect:/login"; // Redireccionar si no hay autenticación
//            }
//
//            // Obtener el usuario autenticado
//            String emailAddress = authentication.getName();
//            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
//            if (!optionalUser.isPresent()) {
//                return "redirect:/login"; // Redireccionar si el usuario no está presente
//            }
//            User user = optionalUser.get();
//            model.addAttribute("user", user);
//
//            // Validar y obtener las preguntas filtradas sin respuesta si es necesario
//            List<Post> posts;
//            if (unanswered != null && unanswered) {
//                posts = postRepository.findUnansweredPosts(pageable);
//            } else {
//                posts = postRepository.findAll();
//            }
//
//            model.addAttribute("posts", posts);
//
//            // Redirigir a la vista de preguntas con el filtro aplicado
//            return "redirect:/preguntas?unanswered=" + (unanswered != null && unanswered);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error"; // Manejar adecuadamente el error en producción
//        }
//    }



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
