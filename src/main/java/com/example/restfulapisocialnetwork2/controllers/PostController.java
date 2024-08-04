package com.example.restfulapisocialnetwork2.controllers;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.*;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.Report;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.responses.PostListResponse;
import com.example.restfulapisocialnetwork2.responses.PostResponse;
import com.example.restfulapisocialnetwork2.services.PostService;
import com.example.restfulapisocialnetwork2.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("${api.prefix}/posts")
@AllArgsConstructor
@RequestMapping("/api_social_network/v1/posts")
public class PostController {
    private final PostService postService;
    private final UserSession userSession;

    //  private final UserService userService;
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

    @GetMapping("/get_post/{id}")
    public ResponseEntity<?> getPost(
            @Valid
            @PathVariable long id
    ) {
        try {
            PostResponse postResponse = postService.getPost(id);
            return ResponseEntity.ok(postResponse);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get_list_posts")
    public ResponseEntity<?> getListPosts(
            @Valid @RequestBody ListPostDTO listPostDTO,
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
            PostListResponse postListResponse = postService.GetListPost(listPostDTO.getId(), listPostDTO.getCount());
            return ResponseEntity.ok(postListResponse);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check_new_item/{lastId}")
    public ResponseEntity<?> checkNewItem(
            @Valid
            @PathVariable long lastId
    ) {
        try {
            PostListResponse postListResponse = postService.GetListPost(lastId, 3);
            return ResponseEntity.ok(postListResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit_post")
    public ResponseEntity<?> editPost(
            @Valid @RequestBody PostEditDTO postEditDTO,
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
            Post post = postService.updatePost(postEditDTO);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete_post/{id}")
    public ResponseEntity<?> deletePost(
            @Valid
            @PathVariable long id
    ) {
        try {
            postService.deleterPost(id);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportPost(
            @Valid @RequestBody ReportDTO reportDTO,
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
            Report report = postService.reportPost(reportDTO);
            return ResponseEntity.ok(report);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
