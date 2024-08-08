package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.dtos.PostEditDTO;
import com.example.restfulapisocialnetwork2.dtos.PostImageDTO;
import com.example.restfulapisocialnetwork2.dtos.ReportDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.exceptions.PermissionDenyException;
import com.example.restfulapisocialnetwork2.models.*;
import com.example.restfulapisocialnetwork2.repositories.ImageRepository;
import com.example.restfulapisocialnetwork2.repositories.LikeRepository;
import com.example.restfulapisocialnetwork2.repositories.PostRepository;
import com.example.restfulapisocialnetwork2.repositories.ReportRepository;
import com.example.restfulapisocialnetwork2.responses.CommentListResponse;
import com.example.restfulapisocialnetwork2.responses.PostListResponse;
import com.example.restfulapisocialnetwork2.responses.PostResponse;
import com.example.restfulapisocialnetwork2.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserSession userSession;
    private final ModelMapper modelMapper;
    private final ReportRepository reportRepository;
    private final LikeRepository likeRepository;
    private final ImageRepository imageRepository;

    public Post createPost(PostDTO postDTO, User user) throws DataNotFoundException {
        Post newPost = Post.builder()
                .userId(user.getId())
                .described(postDTO.getDescribed())
                .build();
        return postRepository.save(newPost);
    }

    @Override
    public PostResponse getPost(long id) throws Exception {
        Optional<Post> listPosts = postRepository.findById(id);
        if (listPosts.isEmpty()) {
            throw new DataIntegrityViolationException("Don't exists this post");
        }
        Post curPost = listPosts.get();
        User user = userSession.GetUser();
        UserResponse userOwner = userService.GetUser(curPost.getUserId());
        PostResponse postResponse = PostResponse.fromPost(curPost, userOwner, user.getId());
        return postResponse;
    }

    @Override
    public PostListResponse GetListPost(Long index, int count) throws Exception {
        // int pageIndex = index.intValue();
        //       int pageSize = count;
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        // Page<Post> pageResult = postRepository.findAll(pageRequest);
        Page<Post> pageResult = postRepository.findByUserId(userSession.GetUser().getId(), pageRequest);
        if (pageResult.isEmpty()) {
            throw new DataIntegrityViolationException("Don't exists this post");
        }
        List<Post> posts = pageResult.getContent();
        List<Post> filteredPosts = posts.stream()
                .skip(index)  // Bỏ qua bản ghi đầu tiên
                .limit(count) // Lấy 3 bản ghi tiếp theo
                .collect(Collectors.toList());
        List<PostResponse> listPostResponse = new ArrayList<>();
        for (Post post : filteredPosts) {
            UserResponse userOwner = userService.GetUser(post.getUserId());
            PostResponse postResponse = PostResponse.fromPost(post, userOwner, userSession.GetUser().getId());
            listPostResponse.add(postResponse);
        }
        return PostListResponse.builder()
                .postResponseList(listPostResponse)
                .newItems(listPostResponse.size())
                .build();
    }

    @Override
    public Post updatePost(PostEditDTO postEditDTO) throws DataNotFoundException {
        Post post = postRepository.findById(postEditDTO.getId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with it: " + postEditDTO.getId()));

        if (post.getUserId() == userSession.GetUser().getId()) {
            new PermissionDenyException("Not Access");
        }
        modelMapper.typeMap(PostEditDTO.class, Post.class)
                .addMappings(
                        mapper -> mapper.skip(Post::setId)
                );
        modelMapper.map(postEditDTO, post);
        return postRepository.save(post);
    }

    @Override
    public void deleterPost(Long id) throws DataNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with it: " + id));
        // no hard-delete, => please soft-delete
        if (post != null) {
            postRepository.deleteById(id);
            postRepository.save(post);
        }
    }

    @Override
    public Report reportPost(ReportDTO reportDTO) throws DataNotFoundException {
        Post post = postRepository.findById(reportDTO.getId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with it: " + reportDTO.getId()));
        Report report = Report.builder()
                .userId(userSession.GetUser().getId())
                .postId(reportDTO.getId())
                .content(reportDTO.getContent())
                .build();
        return reportRepository.save(report);
    }

    @Override
    public Image createPostImage(Long postId, PostImageDTO postImageDTO) throws Exception {

        Post existingPost = postRepository
                .findById(postImageDTO.getPostId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find product with id: " + postImageDTO.getPostId()));

        Image newProductImage = Image
                .builder()
                .post(existingPost)
                .linkImage(postImageDTO.getLinkImage())
                .build();

        // ko cho insert qua 5 anh cho 1 san pham
        int size = imageRepository.findByPostId(existingPost.getId()).size();
        if (size >= 5) {
            throw new InvalidParameterException("Number of images must be <=5");
        }
        return imageRepository.save(newProductImage);
    }


}
