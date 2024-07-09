package com.example.conexionVallejo.controlador;


import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.Report;
import com.example.conexionVallejo.modelos.SavedPost;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.PostRepository;
import com.example.conexionVallejo.repositorios.ReportRepository;
import com.example.conexionVallejo.repositorios.SavedPostRepository;
import com.example.conexionVallejo.repositorios.UserRepository;
import com.example.conexionVallejo.servicios.AgeCalculatorService;
import com.example.conexionVallejo.servicios.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SavedPostRepository savedPostRepository;
    @Autowired
    private PostService postService;
    @GetMapping("/denuncias")
    public String reportsPanel(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String emailAddress = authentication.getName();
        User user = userRepository.findByEmailAddress(emailAddress).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        int pageSize = 10; // Tamaño de página
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdDate").descending());
        Page<Report> reportPage = reportRepository.findAll(pageable);

        model.addAttribute("user", user);
        model.addAttribute("reportList", reportPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reportPage.getTotalPages());

        return "reports";
    }
    @GetMapping("/verPublicacion/{id}")
    public String verPublicacion(@PathVariable Long id, Model model) {
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

                    // Verificar si el post está guardado por el usuario
                    Optional<SavedPost> savedPostOptional = savedPostRepository.findByUserAndPost(user, post);
                    boolean isSaved = savedPostOptional.isPresent();
                    model.addAttribute("isSaved", isSaved);

                    // Verificar si el post está reportado por el usuario
                    boolean hasReported = reportRepository.existsByUserAndPost(user, post);
                    model.addAttribute("hasReported", hasReported);

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

}
