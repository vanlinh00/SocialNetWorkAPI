package com.example.restfulapisocialnetwork2.controllers;

import com.example.restfulapisocialnetwork2.dtos.AcceptFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.dtos.RequestFriendDTO;
import com.example.restfulapisocialnetwork2.models.Friend;
import com.example.restfulapisocialnetwork2.models.RequestedFriend;
import com.example.restfulapisocialnetwork2.responses.CommentListResponse;
import com.example.restfulapisocialnetwork2.services.FriendService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/set_request_friend/{id}")
    public ResponseEntity<?> SetRequestFriend(
            @Valid
            @PathVariable long id
    ) {
        try {
            friendService.SetRequestFriend(id);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get_requested_friend")
    public ResponseEntity<?> GetRequestFriend(
            @Valid @RequestBody RequestFriendDTO requestFriendDTO,
            BindingResult result
    ) {
        try {
            friendService.GetRequestedFriend(requestFriendDTO);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/set_accept_friend")
    public ResponseEntity<?> setAcceptFriend(
            @Valid @RequestBody AcceptFriendDTO acceptFriendDTO,
            BindingResult result
    ) {
        try {
            friendService.SetAcceptFriend(acceptFriendDTO);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
