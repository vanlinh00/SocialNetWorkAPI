package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.PostDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.repositories.PostRepository;
import com.example.restfulapisocialnetwork2.responses.PostListResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Override
    public Post createPost(PostDTO postDTO, User user) throws DataNotFoundException {
        Post newPost = Post.builder()
                .userId(user.getId())
                .described(postDTO.getDescribed())
                .build();
        return postRepository.save(newPost);
    }

    @Override
    public Post getPost(long id) throws DataNotFoundException {
        Optional<Post> listPosts = postRepository.findById(id);
        if (listPosts.isEmpty()) {
            throw new DataIntegrityViolationException("Don't exists this post");
        }
        return listPosts.get();
    }


    //            Post post = postService.getPost(id);
//            User userOwner = userService.GetUser(post.getUserId());
    @Override
    public   List<Post> GetListPost(Long index,int count) throws DataNotFoundException {
        // Tạo PageRequest với kích thước trang là 11 (từ 5 đến 15 có 11 phần tử)
        int pageSize = 11;
        // Page index bắt đầu từ 0, nên để bắt đầu từ phần tử thứ 5, cần tính toán số trang
        // (0-based index): (startElementIndex) / pageSize
        int pageIndex = 4 / pageSize;

        // Tạo PageRequest
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);

        // Lấy dữ liệu
        Page<Post> pageResult = postRepository.findAll(pageRequest);

        // Lấy danh sách Post từ Page
        List<Post> posts = pageResult.getContent();

        return posts;
    }

}
