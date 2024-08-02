package com.example.restfulapisocialnetwork2.controllers;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.dtos.UserDTO;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.services.PostService;
import com.example.restfulapisocialnetwork2.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/posts")
@AllArgsConstructor

public class PostController {
    private final PostService postService;
    private final UserSession userSession;

    @PostMapping("/add_post")
    public ResponseEntity<?> createPost(
            @Valid @RequestBody PostDTO postDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            User user = userSession.GetUser();
            postService.createPost(postDTO, user);
            return ResponseEntity.ok("Registers succesfully");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/get_post/{id}")
    public ResponseEntity<?> getPost(
            @Valid
            @PathVariable long id,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            User user = userSession.GetUser();
            Post post= postService.getPost(id);
            return ResponseEntity.ok(post);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
