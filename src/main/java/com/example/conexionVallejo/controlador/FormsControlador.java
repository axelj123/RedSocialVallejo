package com.example.conexionVallejo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormsControlador {

	
	@GetMapping ("/inicio")
	public String inicio () {
		
		return "index";
	}
	
	@GetMapping ("/foro")
	public String foro () {
		
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
	
}
