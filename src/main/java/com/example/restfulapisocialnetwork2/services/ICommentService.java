package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Comment;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.responses.CommentListResponse;
import com.example.restfulapisocialnetwork2.responses.CommentResponse;

import java.util.List;

public interface ICommentService {
    Comment CreateComment(CommentDTO commentDTO) throws DataNotFoundException;

    CommentListResponse GetComment(Long id) throws Exception;
}
