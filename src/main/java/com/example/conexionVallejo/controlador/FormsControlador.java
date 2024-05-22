package com.example.conexionVallejo.controlador;

import java.util.Iterator;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.servicios.PostService;

@Controller
public class FormsControlador {

	@Autowired
	private SessionRegistry sessionRegistry;

	private final PostService postService;

	public FormsControlador(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/")
	public String root() {
		return "redirect:/inicio";
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

	/*
	 * @GetMapping("/login") public String login() { return "loginregister"; }
	 */

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
	public String Guardados(Model model) {
		return "redirect:/perfil?tab=guardados";
	}

	@GetMapping("/perfil/configuracion")
	public String perfilConfiguracion(Model model) {
		return "redirect:/perfil?tab=configuracion";
	}

	@GetMapping("/session")
	public ResponseEntity<?> getDetailsSession() {

		String sessionId = "";
		User userObject = null;

		List<Object> sessions = sessionRegistry.getAllPrincipals();
		
		for (Object session :  sessions) {
		if (session instanceof User) {
			userObject=(User) session;
			
		}
			
		}
		return (ResponseEntity<?>) ResponseEntity.ok();
	}

}
