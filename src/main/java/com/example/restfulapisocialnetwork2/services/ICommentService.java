package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataNotFoundException;
import com.example.restfulapisocialnetwork2.models.Comment;

public interface ICommentService {
    Comment CreateComment(CommentDTO commentDTO) throws DataNotFoundException;
}
