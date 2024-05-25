package com.example.conexionVallejo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.conexionVallejo.modelos.Tag;
import com.example.conexionVallejo.repositorios.TagsRepository;

@Service
public class TagService {

    private final TagsRepository tagsRepository;

    @Autowired
    public TagService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<String> obtenerNombresTags() {
        List<Tag> tags = tagsRepository.findAll();
        return tags.stream().map(Tag::getTagName).collect(Collectors.toList());
    }

    public List<Long> convertirNombresAIds(List<String> nombresTags) {
        List<Long> idsTags = new ArrayList<>();

        for (String nombreTag : nombresTags) {
            Tag tag = tagsRepository.findByTagName(nombreTag);
            if (tag != null) {
                idsTags.add(tag.getId());
            }
        }

        return idsTags;
    }
}
