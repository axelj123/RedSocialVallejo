package com.example.conexionVallejo.controlador;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.servicios.PostService;

@Controller
public class FormsControlador {

    private final PostService postService;

    public FormsControlador(PostService postService) {
        this.postService = postService;
    }
	@GetMapping ("/inicio")
	public String inicio () {
		
		return "index";
	}
	  @GetMapping("/foro")
	    public String foro(Model model) {
	        List<Post> posts = postService.obtenerPreguntas();
	        model.addAttribute("posts", posts);
	        return "foro";
	    }
	
	@GetMapping ("/posts/ask")
	public String ask() {
		return "ask";
	}
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	@GetMapping("/perfil")
	public String perfil() {
		return "Perfil";
	}
}
