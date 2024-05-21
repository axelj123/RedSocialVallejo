package com.example.conexionVallejo.controlador;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.servicios.PostService;
@Controller
public class FormsControlador {

    private final PostService postService;

    public FormsControlador(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "index";
    }

    @GetMapping("/foro")
    public String foro(Model model) {
        List<Post> posts = postService.obtenerPreguntas();
        model.addAttribute("posts", posts);
        return "foro";
    }

 
    @GetMapping("/login")
    public String login() {
        return "loginregister";
    }

    @GetMapping("/perfil")
    public String perfil(@RequestParam(value = "tab", defaultValue = "info") String tab, Model model) {
        model.addAttribute("activeTab", tab);
        return "Perfil";
    }

    @GetMapping("/perfil/info")
    public String perfilInfo(Model model) {
        return "redirect:/perfil?tab=info";
    }

    @GetMapping("/perfil/actividades")
    public String perfilActividades(Model model) {
        return "redirect:/perfil?tab=actividades";
    }
    
    @GetMapping("/perfil/guardados")
    public String Guardados (Model model) {
    	return  "redirect:/perfil?tab=guardados";
    }

    @GetMapping("/perfil/configuracion")
    public String perfilConfiguracion(Model model) {
        return "redirect:/perfil?tab=configuracion";
    }
    
    
}
