package com.example.conexionVallejo.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormsControlador {

	
	@GetMapping ("/inicio")
	public String inicio () {
		
		return "index";
	}
	
}
