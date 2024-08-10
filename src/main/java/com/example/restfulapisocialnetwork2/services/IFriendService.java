package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.AcceptFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.dtos.RequestFriendDTO;
import com.example.restfulapisocialnetwork2.models.Comment;
import com.example.restfulapisocialnetwork2.responses.BaseReponse;
import com.example.restfulapisocialnetwork2.responses.FriendListResponse;
import com.example.restfulapisocialnetwork2.responses.UserInfoResponse;

public interface IFriendService {
    int SetRequestFriend(Long userId) throws Exception;

    FriendListResponse GetRequestedFriend(RequestFriendDTO requestFriendDTO) throws Exception;

    void SetAcceptFriend(AcceptFriendDTO acceptFriendDTO) throws Exception;

    FriendListResponse GetUserFriends(RequestFriendDTO requestFriendDTO) throws Exception;

    UserInfoResponse GetUserInfo(Long userIdFriend) throws Exception;
}
