package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.dtos.AcceptFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.RequestFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.UserInfoDTO;
import com.example.restfulapisocialnetwork2.models.UserInfo;
import com.example.restfulapisocialnetwork2.responses.FriendListResponse;
import com.example.restfulapisocialnetwork2.responses.UserInfoResponse;

public interface IFriendService {
    int SetRequestFriend(Long userId) throws Exception;

    FriendListResponse GetRequestedFriend(RequestFriendDTO requestFriendDTO) throws Exception;

    void SetAcceptFriend(AcceptFriendDTO acceptFriendDTO) throws Exception;

    FriendListResponse GetUserFriends(RequestFriendDTO requestFriendDTO) throws Exception;

    UserInfoResponse GetUserInfo(Long userIdFriend) throws Exception;

    UserInfo SetUserInfo(UserInfoDTO userInfoDTO) throws Exception;

    void EditUserInfo(UserInfoDTO userInfoDTO) throws Exception;
}
