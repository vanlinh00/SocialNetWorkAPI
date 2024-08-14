package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.*;
import com.example.restfulapisocialnetwork2.exceptions.*;
import com.example.restfulapisocialnetwork2.models.*;
import com.example.restfulapisocialnetwork2.repositories.*;
import com.example.restfulapisocialnetwork2.responses.PostListResponse;
import com.example.restfulapisocialnetwork2.responses.PostResponse;
import com.example.restfulapisocialnetwork2.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private final BlockRepository blockRepository;


    private PostDocumentRepository postDocumentRepository;


    public void createPost(PostDTO postDTO, User user) throws Exception {
        Post newPost = Post.builder()
                .userId(user.getId())
                .described(postDTO.getDescribed())
                .build();
        // Save to Elasticsearch
        PostDocument postDocument = PostDocument.builder()
                .id(String.valueOf(newPost.getId()))
                .userId(newPost.getUserId())
                .described(newPost.getDescribed())
                .build();
        postDocumentRepository.save(postDocument);

        postRepository.save(newPost);
    }

    @Override
    public PostResponse getPost(long id) throws Exception {
        Optional<Post> listPosts = postRepository.findById(id);
        if (listPosts.isEmpty()) {
            new ResourceNotFoundException("This post does not exist");
        }
        Post curPost = listPosts.get();
        User user = userSession.GetUser();
        UserResponse userOwner = userService.getUser(curPost.getUserId());
        boolean blockUser = blockRepository.existsByUserIdAndBlockedUserId(user.getId(), curPost.getUserId());
        PostResponse postResponse = PostResponse.fromPost(curPost, userOwner, user.getId(), blockUser);
        return postResponse;
    }

    @Override
    public PostListResponse GetListPost(Long index, int count) throws Exception {

        //   PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        //   Page<Post> pageResult = postRepository.findByUserId(userSession.GetUser().getId(), pageRequest);

        Pageable pageable = PageRequest.of((int) (index - 1), count);
        Page<Post> pageResult = postRepository.findAll(pageable);
        if (pageResult.isEmpty()) {
            new ResourceNotFoundException("This post does not exist");
        }

        List<Post> posts = pageResult.getContent();
//        List<Post> filteredPosts = posts.stream()
//                .skip(index)
//                .limit(count)
//                .collect(Collectors.toList());
        List<PostResponse> listPostResponse = new ArrayList<>();
        for (Post post : posts) {
            UserResponse userOwner = userService.getUser(post.getUserId());
            boolean blockUser = blockRepository.existsByUserIdAndBlockedUserId(userSession.GetUser().getId(), post.getUserId());
            PostResponse postResponse = PostResponse.fromPost(post, userOwner, userSession.GetUser().getId(), blockUser);
            listPostResponse.add(postResponse);
        }
        return PostListResponse.builder()
                .postResponseList(listPostResponse)
                .newItems(listPostResponse.size())
                .build();
    }

    @Override
    public void updatePost(PostEditDTO postEditDTO) throws Exception {
        Post post = postRepository.findById(postEditDTO.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Cannot find order with it: " + postEditDTO.getId())
                );

        if (post.getUserId() != userSession.GetUser().getId()) {
            throw new ForbiddenAccessException("You are not authorized to perform this action");
        }
        modelMapper.typeMap(PostEditDTO.class, Post.class)
                .addMappings(
                        mapper -> mapper.skip(Post::setId)
                );
        modelMapper.map(postEditDTO, post);
        postRepository.save(post);
    }

    @Override
    public void deleterPost(Long id) throws Exception {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find order with it: " + id));
        // no hard-delete, => please soft-delete
        if (post != null) {
            postRepository.deleteById(id);
            postRepository.save(post);
        }
    }

    @Override
    public Report reportPost(ReportDTO reportDTO) throws Exception {
        Post post = postRepository.findById(reportDTO.getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Cannot find order with it: " + reportDTO.getId())
                );
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
                        new ResourceNotFoundException("Cannot find product with id: " + postImageDTO.getPostId())
                );
        Image newProductImage = Image
                .builder()
                .post(existingPost)
                .linkImage(postImageDTO.getLinkImage())
                .build();
        int size = imageRepository.findByPostId(existingPost.getId()).size();
        if (size >= 5) {
            throw new BadRequestException("Number of images must be <=5");
        }
        return imageRepository.save(newProductImage);
    }

    @Override
    public PostListResponse searchPost(SearchPostDTO searchPostDTO) throws Exception {
        List<PostDocument> listPostDocument = postDocumentRepository.findByDescribedContainingIgnoreCase(searchPostDTO.getKeyWord());
        List<PostResponse> listPostResponse = new ArrayList<>();
        for (PostDocument document : listPostDocument) {
            PostResponse postResponse = getPost(document.getUserId());
            listPostResponse.add(postResponse);
        }
        return PostListResponse.builder()
                .postResponseList(listPostResponse)
                .newItems(listPostResponse.size())
                .build();
    }

}
