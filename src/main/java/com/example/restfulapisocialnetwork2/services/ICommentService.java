package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.dtos.DeleteCommentDTO;
import com.example.restfulapisocialnetwork2.dtos.EditCommentDTO;
import com.example.restfulapisocialnetwork2.models.Comment;
import com.example.restfulapisocialnetwork2.responses.CommentListResponse;
import com.example.restfulapisocialnetwork2.responses.CommentResponse;

public interface ICommentService {
    Comment CreateComment(CommentDTO commentDTO) throws Exception;

    CommentListResponse GetComment(Long id) throws Exception;

    void DeleteComment(DeleteCommentDTO deleteCommentDTO) throws Exception;

    CommentResponse updateComment(EditCommentDTO editCommentDTO) throws Exception;
}
