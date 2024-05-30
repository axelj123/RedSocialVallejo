package com.example.conexionVallejo.controlador;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.conexionVallejo.modelos.Post;
import com.example.conexionVallejo.modelos.PostType;
import com.example.conexionVallejo.modelos.Tag;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.PostRepository;
import com.example.conexionVallejo.repositorios.PostTypeRepository;
import com.example.conexionVallejo.repositorios.TagsRepository;
import com.example.conexionVallejo.repositorios.UserRepository;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostTypeRepository postTypeRepository;

    @Autowired
    private TagsRepository tagsRepository;


    @PostMapping("/post/new")
    public String submitNewPost(@ModelAttribute Post post, @RequestParam("tags") String[] tagIds, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                post.setCreatedByUser(user);

                Optional<PostType> optionalPostType = postTypeRepository.findById(1L);
                if (optionalPostType.isPresent()) {
                    PostType postType = optionalPostType.get();
                    post.setPostType(postType);
                }

                post.setCreatedDate(new Timestamp(System.currentTimeMillis()));

                // Asociar etiquetas a la publicación
                List<Tag> tags = new ArrayList<>();
                for (String tagName : tagIds) {
                    Tag tag = tagsRepository.findByTagName(tagName);
                    if (tag != null) {
                        tags.add(tag);
                    }
                }

                post.setTag(tags);

                // Guardar la publicación
                postRepository.save(post);

                return "redirect:/foro";
            }
        }
        return "redirect:/login";
    }



}
