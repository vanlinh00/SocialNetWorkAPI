package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.dtos.DeleteCommentDTO;
import com.example.restfulapisocialnetwork2.dtos.EditCommentDTO;
import com.example.restfulapisocialnetwork2.exceptions.ForbiddenAccessException;
import com.example.restfulapisocialnetwork2.exceptions.ResourceNotFoundException;
import com.example.restfulapisocialnetwork2.models.Comment;
import com.example.restfulapisocialnetwork2.models.Post;
import com.example.restfulapisocialnetwork2.repositories.CommentRepository;
import com.example.restfulapisocialnetwork2.repositories.PostRepository;
import com.example.restfulapisocialnetwork2.responses.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final PostService postService;

    @Override
    public Comment CreateComment(CommentDTO commentDTO) throws Exception {

        Optional<Post> listPosts = postRepository.findById(commentDTO.getId());
        if (listPosts.isEmpty()) {
            new ResourceNotFoundException("This post does not exist");
        }
        Post currentPos = listPosts.get();

        PostResponse postResponse = postService.getPost(commentDTO.getId());
        if (postResponse.getIsBlocked() == 1) {
            throw new ForbiddenAccessException("You are not authorized to perform this action");
        }
        Comment newComment = Comment.builder()
                .userId(userSession.GetUser().getId())
                .post(currentPos)
                .content(commentDTO.getComment())
                .build();
        return commentRepository.save(newComment);
    }

    @Override
    public CommentListResponse GetComment(Long id) throws Exception {
        PostResponse postResponse = postService.getPost(id);
        List<Comment> listComment = commentRepository.findByPostId(id);
        List<CommentResponse> listPostResponse = new ArrayList<>();
        for (Comment Comment : listComment) {
            UserResponse userResponse = userService.GetUser(Comment.getUserId());
            CommentResponse commentResponse = CommentResponse.fromComment(Comment, userResponse);
            listPostResponse.add(commentResponse);
        }
        int IsBlock = postResponse.getIsBlocked();
        return CommentListResponse.builder()
                .commentResponseList(listPostResponse)
                .isBlock(IsBlock)
                .build();
    }

    @Override
    public void DeleteComment(DeleteCommentDTO deleteCommentDTO) throws Exception {
        Optional<Post> listPosts = postRepository.findById(deleteCommentDTO.getId());
        if (listPosts.isEmpty()) {
            new ResourceNotFoundException("This post does not exist");
        }
        Post currentPost = listPosts.get();
        if (currentPost.getUserId() == userSession.GetUser().getId()) {
            throw new ForbiddenAccessException("You are not authorized to perform this action");
        }
        Optional<Comment> comments =
                commentRepository.findByUserIdAndPostId(deleteCommentDTO.getId(), deleteCommentDTO.getIdCom());
        if (comments.isEmpty()) {
            // throw new DataIntegrityViolationException("Don't exists this comments");
            new ResourceNotFoundException("Don't exists this comments");
        }
    }

    @Override
    public CommentResponse updateComment(EditCommentDTO editCommentDTO)
            throws Exception {

        Optional<Post> listPosts = postRepository.findById(editCommentDTO.getId());
        if (listPosts.isEmpty()) {
            new ResourceNotFoundException("This post does not exist");
        }
        Comment comment = commentRepository
                .findByUserIdAndPostId(editCommentDTO.getId(), editCommentDTO.getIdCom())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Cannot find comment with id post: "
                                        + editCommentDTO.getId() + " and id comment: " + editCommentDTO.getIdCom()
                        ));

        UserResponse userResponse = userService.GetUser(comment.getUserId());
        if (comment.getUserId() == userSession.GetUser().getId()) {
            throw new ForbiddenAccessException("You are not authorized to perform this action");
        }
        modelMapper.typeMap(EditCommentDTO.class, Comment.class)
                .addMappings(
                        mapper -> mapper.skip(Comment::setId)
                );
        modelMapper.map(editCommentDTO, comment);
        commentRepository.save(comment);
        return CommentResponse.fromComment(comment, userResponse);
    }
}
