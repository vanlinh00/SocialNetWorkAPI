package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Comment;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.repositories.CommentRepository;
import com.example.restfulapisocialnetwork2.repositories.PostRepository;
import com.example.restfulapisocialnetwork2.responses.*;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService implements ICommentService {

    private final UserSession userSession;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Comment CreateComment(CommentDTO commentDTO) throws DataNotFoundException {

        Optional<Post> listPosts = postRepository.findById(commentDTO.getId());

        if (listPosts.isEmpty()) {
            throw new DataIntegrityViolationException("Don't exists this post");
        }

        Comment newComment = Comment.builder()
                .userId(userSession.GetUser().getId())
                .postId(commentDTO.getId())
                .content(commentDTO.getComment())
                .build();
        return commentRepository.save(newComment);
    }

    @Override
    public CommentListResponse GetComment(Long id) throws Exception {
        List<Comment> listComment = commentRepository.findByPostId(id);
        List<CommentResponse> listPostResponse = new ArrayList<>();
        for (Comment Comment : listComment) {
            UserResponse userResponse = userService.GetUser(Comment.getUserId());
            CommentResponse commentResponse = CommentResponse.fromComment(Comment, userResponse);
            listPostResponse.add(commentResponse);
        }
        int IsBlock = 0;// khóa
        return CommentListResponse.builder()
                .commentResponseList(listPostResponse)
                .isBlock(IsBlock)
                .build();
    }
}
