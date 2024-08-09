package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.BlockDTO;
import org.springframework.http.ResponseEntity;

public interface IBlockService {
    ResponseEntity<?> blockUser(BlockDTO blockDTO ) throws Exception;
}
