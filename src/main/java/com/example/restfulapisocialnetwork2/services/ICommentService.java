package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.dtos.DeleteCommentDTO;
import com.example.restfulapisocialnetwork2.dtos.EditCommentDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Comment;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.responses.CommentListResponse;
import com.example.restfulapisocialnetwork2.responses.CommentResponse;

import java.util.List;

public interface ICommentService {
    Comment CreateComment(CommentDTO commentDTO) throws DataNotFoundException;

    CommentListResponse GetComment(Long id) throws Exception;

    void DeleteComment(DeleteCommentDTO deleteCommentDTO) throws DataNotFoundException;

    CommentResponse updatePost(EditCommentDTO editCommentDTO) throws Exception;
}
