package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.AcceptFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.dtos.RequestFriendDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataAlreadyExistsException;
import com.example.restfulapisocialnetwork2.exceptions.ForbiddenAccessException;
import com.example.restfulapisocialnetwork2.exceptions.ResourceNotFoundException;
import com.example.restfulapisocialnetwork2.models.*;
import com.example.restfulapisocialnetwork2.repositories.FriendRepository;
import com.example.restfulapisocialnetwork2.repositories.RequestedFriendRepository;
import com.example.restfulapisocialnetwork2.repositories.UserRepository;
import com.example.restfulapisocialnetwork2.responses.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendService implements IFriendService {
    private final RequestedFriendRepository requestedFriendRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserSession userSession;

    @Override
    public int SetRequestFriend(Long userIdFriend) throws Exception {
        Optional<User> userFriend = userRepository.findById(userIdFriend);
        if (userFriend.isEmpty()) {
            new ResourceNotFoundException("This user does not exist");
        }
        User user = userSession.GetUser();
        if (requestedFriendRepository.existsByUserIdAAndUserIdB(user.getId(), userIdFriend)) {
            new DataAlreadyExistsException("This data exist");
        }
        RequestedFriend requestedFriend = RequestedFriend.builder()
                .userIdA(user.getId())
                .userIdB(userIdFriend)
                .build();
        requestedFriendRepository.save(requestedFriend);
        List<RequestedFriend> requestedFriendList = requestedFriendRepository.findByUserIdA(user.getId());
        return requestedFriendList.size();
    }

    @Override
    public FriendListResponse GetRequestedFriend(RequestFriendDTO requestFriendDTO) throws Exception {

        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        Page<RequestedFriend> pageResult = requestedFriendRepository.findByUserIdA(userSession.GetUser().getId(), pageRequest);
        if (pageResult.isEmpty()) {
            new ResourceNotFoundException("This user does not exist");
        }
        List<RequestedFriend> requestedFriend = pageResult.getContent();
        List<RequestedFriend> filteredPosts = requestedFriend.stream()
                .skip(requestFriendDTO.getIndex())
                .limit(requestFriendDTO.getCount())
                .collect(Collectors.toList());

        List<FriendResponse> listFriendResponse = new ArrayList<>();
        for (RequestedFriend rqFriend : filteredPosts) {

            Optional<User> userOptional = userRepository.findById(rqFriend.getUserIdB());
            User userB = userOptional.get();
            FriendResponse friendResponse = FriendResponse
                    .builder()
                    .id(userB.getId())
                    .userName(userB.getFullName())
                    .avatar(userB.getAddress())
                    .created(rqFriend.getCreatedAt())
                    .build();
            listFriendResponse.add(friendResponse);
        }
        return FriendListResponse.builder()
                .postResponseList(listFriendResponse)
                .countFriend(listFriendResponse.size())
                .build();
    }

    @Override
    public void SetAcceptFriend(AcceptFriendDTO acceptFriendDTO) throws Exception {

        Optional<User> userFriendOptional = userRepository.findById(acceptFriendDTO.getUserId());
        if (userFriendOptional.isEmpty()) {
            new ResourceNotFoundException("This user does not exist");
        }
        User user = userFriendOptional.get();
        User currentUser = userSession.GetUser();
        requestedFriendRepository.deleteByUserIdAAndUserIdB(
                currentUser.getId(), acceptFriendDTO.getUserId());
        if (acceptFriendDTO.getIsAccept() == 1) {
            Friend friend = Friend.builder()
                    .userIdA(currentUser.getId())
                    .userIdB(acceptFriendDTO.getUserId())
                    .build();
            friendRepository.save(friend);
        }
        // else if (acceptFriendDTO.getIsAccept() == 0) {
//        }
    }

    @Override
    public FriendListResponse GetUserFriends(RequestFriendDTO requestFriendDTO)
            throws Exception {
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);

        Long idUserGetFriend = (requestFriendDTO.getUserId() == 0) ? userSession.GetUser().getId() : requestFriendDTO.getUserId();
        Page<Friend> pageResult =
                friendRepository.findByUserIdA(idUserGetFriend, pageRequest);
        if (pageResult.isEmpty()) {
            new ResourceNotFoundException("This user does not exist");
        }

        List<Friend> listFriend = pageResult.getContent();
        List<Friend> filteredPosts = listFriend.stream()
                .skip(requestFriendDTO.getIndex())
                .limit(requestFriendDTO.getCount())
                .collect(Collectors.toList());
        List<FriendResponse> listFriendResponse = new ArrayList<>();

        for (Friend rqFriend : filteredPosts) {
            Optional<User> userOptional = userRepository.findById(rqFriend.getUserIdB());
            User userB = userOptional.get();
            FriendResponse friendResponse = FriendResponse
                    .builder()
                    .id(userB.getId())
                    .userName(userB.getFullName())
                    .avatar(userB.getAddress())
                    .created(rqFriend.getCreatedAt())
                    .build();
            listFriendResponse.add(friendResponse);
        }

        return FriendListResponse.builder()
                .postResponseList(listFriendResponse)
                .countFriend(listFriendResponse.size())
                .build();
    }

    @Override
    public int getUserInfo(Long userIdFriend) throws Exception {
        Optional<User> userFriend = userRepository.findById(userIdFriend);
        if (userFriend.isEmpty()) {
            new ResourceNotFoundException("This user does not exist");
        }
        User user = userSession.GetUser();
        if (requestedFriendRepository.existsByUserIdAAndUserIdB(user.getId(), userIdFriend)) {
            new DataAlreadyExistsException("This data exist");
        }
        RequestedFriend requestedFriend = RequestedFriend.builder()
                .userIdA(user.getId())
                .userIdB(userIdFriend)
                .build();
        requestedFriendRepository.save(requestedFriend);
        List<RequestedFriend> requestedFriendList = requestedFriendRepository.findByUserIdA(user.getId());
        return requestedFriendList.size();
    }
}

