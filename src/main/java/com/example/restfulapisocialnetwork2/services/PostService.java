package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.repositories.PostRepository;
import com.example.restfulapisocialnetwork2.responses.PostResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
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
        User userOwner = userService.GetUser(curPost.getUserId());
        PostResponse postResponse = PostResponse.fromPost(curPost, userOwner);
        return postResponse;
    }

    @Override
    public List<PostResponse> GetListPost(Long index, int count) throws Exception {
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
            User userOwner = userService.GetUser(post.getUserId());
            PostResponse postResponse = PostResponse.fromPost(post, userOwner);
            listPostResponse.add(postResponse);
        }
        return listPostResponse;
    }

}
