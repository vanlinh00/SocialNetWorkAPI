package com.example.restfulapisocialnetwork2.controllers;

import com.example.restfulapisocialnetwork2.dtos.AcceptFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.RequestFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.UserInfoDTO;
import com.example.restfulapisocialnetwork2.models.UserInfo;
import com.example.restfulapisocialnetwork2.responses.FriendListResponse;
import com.example.restfulapisocialnetwork2.responses.UserInfoResponse;
import com.example.restfulapisocialnetwork2.services.FriendService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${api.prefix}/friends")

@AllArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final ModelMapper modelMapper;

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

    @GetMapping("/get_user_info/{id}")
    public ResponseEntity<?> getUserInfo(
            @Valid
            @PathVariable long id
    ) {
        try {
            UserInfoResponse userInfoResponse = friendService.GetUserInfo(id);
            return ResponseEntity.ok(userInfoResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/set_user_info")
    public ResponseEntity<?> setUserInfo(
            @Valid
            @RequestBody UserInfoDTO userInfoDTO
    ) {
        try {
            friendService.SetUserInfo(userInfoDTO);
            return ResponseEntity.ok("Ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit_user_info")
    public ResponseEntity<?> editUserInfo(
            @Valid
            @RequestBody UserInfoDTO userInfoDTO
    ) {
        try {
            friendService.EditUserInfo(userInfoDTO);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
