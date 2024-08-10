package com.example.restfulapisocialnetwork2.controllers;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.*;
import com.example.restfulapisocialnetwork2.models.Image;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.Report;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.responses.ImageListResponse;
import com.example.restfulapisocialnetwork2.responses.ImageResponse;
import com.example.restfulapisocialnetwork2.responses.PostListResponse;
import com.example.restfulapisocialnetwork2.responses.PostResponse;
import com.example.restfulapisocialnetwork2.services.LikeService;
import com.example.restfulapisocialnetwork2.services.PostService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/api_social_network/v1/posts")
public class PostController {
    private final PostService postService;
    private final UserSession userSession;
    private final LikeService likeService;

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
            return ResponseEntity.ok("Upload Post succesfully");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/images/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) {
        try {
            PostResponse existingPost = postService.getPost(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            List<ImageResponse> imageResponseList = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() > 10 * 1024 * 1024) {  // Kích tước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File í to large? Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null
                        || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Luu file va cap nhat thumbnail trong DTO
                String filename = storeFile(file);

                // Luu vao doi tuong product trong DB
                Image image = postService.createPostImage(
                        existingPost.getId(),
                        PostImageDTO.builder()
                                .postId(existingPost.getId())
                                .linkImage(filename)
                                .build()
                );
                imageResponseList.add(ImageResponse.fromImage(image));
            }
            return ResponseEntity.ok().body(
                    ImageListResponse.builder()
                            .imageResponseList(imageResponseList)
                            .countImage(imageResponseList.size()).build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // thêm UUID vào trương tên file để đảm bảo tên file là duy nhất
        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn dến thư mực mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");

        // kiểm tra và tạo thư muc nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // Sao chép file vào thư mực đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
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
            postService.updatePost(postEditDTO);
            return ResponseEntity.ok("Edit Post Succesfully");
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

    @GetMapping("/like/{id}")
    public ResponseEntity<?> likePost(
            @Valid
            @PathVariable long id
    ) throws Exception {
        try {
            User user = userSession.GetUser();
            ResponseEntity response = likeService.likePost(id, user.getId());
            return response;
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
