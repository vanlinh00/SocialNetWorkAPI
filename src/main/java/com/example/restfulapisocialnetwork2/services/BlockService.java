package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.BlockDTO;
import com.example.restfulapisocialnetwork2.models.Block;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.repositories.BlockRepository;
import com.example.restfulapisocialnetwork2.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BlockService implements IBlockService {

    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final UserSession userSession;

    @Transactional
    @Override
    public ResponseEntity<?> blockUser(BlockDTO blockDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findById(blockDTO.getUserId());
        if (optionalUser.isEmpty()) {
            throw new DataIntegrityViolationException("don't exists this user");
        }
        User user = userSession.GetUser();
        if (user.getId() == blockDTO.getUserId()) {
            throw new DataIntegrityViolationException("can't block yourself");
        }
        boolean isBlock = blockRepository
                .existsByUserIdAndBlockedUserId(
                        userSession.GetUser().getId(),
                        blockDTO.getUserId());

        if (blockDTO.getType() == 1) {
            if (isBlock) {
                return ResponseEntity.ok().body("User had been blocked");
            } else {
                Block block = new Block();
                block = Block.builder()
                        .userId(user.getId())
                        .blockedUserId(blockDTO.getUserId())
                        .build();
                blockRepository.save(block);
                return ResponseEntity.ok().body("User is now blocked.");
            }

        } else {
            if (isBlock) {
                blockRepository.deleteByUserIdAndBlockedUserId(user.getId(), blockDTO.getUserId());
                return ResponseEntity.ok().body("User is now Unblocked.");
            } else {
                return ResponseEntity.ok().body("User had been Unblocked");
            }
        }

    }
}
