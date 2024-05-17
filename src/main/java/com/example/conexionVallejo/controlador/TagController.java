package com.example.conexionVallejo.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.conexionVallejo.modelos.Tag;
import com.example.conexionVallejo.repositorios.TagsRepository;

@RestController
public class TagController {
TagsRepository tagRepositorio;

public TagController(TagsRepository tagRepositorio) {
	this.tagRepositorio = tagRepositorio;
}

	@GetMapping("/api/tags")
	public List <Tag> obtenerTags(){
		return tagRepositorio.findAll();
	}
	
}
