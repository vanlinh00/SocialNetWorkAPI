package com.example.restfulapisocialnetwork2.controllers;

import com.example.restfulapisocialnetwork2.dtos.AcceptFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.dtos.RequestFriendDTO;
import com.example.restfulapisocialnetwork2.models.Friend;
import com.example.restfulapisocialnetwork2.models.RequestedFriend;
import com.example.restfulapisocialnetwork2.responses.CommentListResponse;
import com.example.restfulapisocialnetwork2.responses.FriendListResponse;
import com.example.restfulapisocialnetwork2.services.FriendService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${api.prefix}/friends")

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
            FriendListResponse friendListResponse = friendService.GetRequestedFriend(requestFriendDTO);
            return ResponseEntity.ok(friendListResponse);
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

    @GetMapping("/get_user_friends")
    public ResponseEntity<?> getUserFriends(
            @Valid @RequestBody RequestFriendDTO requestFriendDTO,
            BindingResult result
    ) {
        try {
            FriendListResponse friendListResponse = friendService.GetUserFriends(requestFriendDTO);
            return ResponseEntity.ok(friendListResponse);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/get_user_info/{id}")
    public ResponseEntity<?> getUserInfo(
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

}
