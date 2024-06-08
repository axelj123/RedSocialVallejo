package com.example.conexionVallejo.controlador;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.conexionVallejo.modelos.*;
import com.example.conexionVallejo.servicios.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.conexionVallejo.repositorios.PostRepository;
import com.example.conexionVallejo.repositorios.PostTypeRepository;
import com.example.conexionVallejo.repositorios.TagsRepository;
import com.example.conexionVallejo.repositorios.UserRepository;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private FileService fileService;


    @PostMapping("/post/new")
    public String submitNewPost(@ModelAttribute Post post,
                                @RequestParam("tags") String[] tagIds
                              ,
                                Authentication authentication) {
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

                List<Tag> tags = new ArrayList<>();
                for (String tagName : tagIds) {
                    Tag tag = tagsRepository.findByTagName(tagName);
                    if (tag != null) {
                        tags.add(tag);
                    }
                }
                post.setTag(tags);

                postRepository.save(post);


                return "redirect:/foro";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/answer/new")
    public String submitAnswer(@RequestParam("parentQuestionId") Long parentQuestionId,
                               @RequestParam("postDetails") String postDetails,
                               Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String emailAddress = authentication.getName();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                Post answer = new Post();
                answer.setCreatedByUser(user);
                answer.setPostDetails(postDetails);
                answer.setCreatedDate(new Timestamp(System.currentTimeMillis()));

                Optional<PostType> optionalPostType = postTypeRepository.findById(2L); // Assuming 2 is the PostType ID for answers
                if (optionalPostType.isPresent()) {
                    PostType postType = optionalPostType.get();
                    answer.setPostType(postType);
                }

                Optional<Post> optionalParentPost = postRepository.findById(parentQuestionId);
                if (optionalParentPost.isPresent()) {
                    Post parentPost = optionalParentPost.get();
                    answer.setParentQuestion(parentPost);
                }

                postRepository.save(answer);

                return "redirect:/postopen/" + parentQuestionId; // Redirect to the original post page
            }
        }
        return "redirect:/login";
    }




}
