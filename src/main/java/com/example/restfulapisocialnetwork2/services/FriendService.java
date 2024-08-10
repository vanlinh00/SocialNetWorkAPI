package com.example.restfulapisocialnetwork2.services;

import com.example.restfulapisocialnetwork2.components.UserSession;
import com.example.restfulapisocialnetwork2.dtos.AcceptFriendDTO;
import com.example.restfulapisocialnetwork2.dtos.CommentDTO;
import com.example.restfulapisocialnetwork2.dtos.DataFriendUserDTO;
import com.example.restfulapisocialnetwork2.dtos.RequestFriendDTO;
import com.example.restfulapisocialnetwork2.exceptions.DataAlreadyExistsException;
import com.example.restfulapisocialnetwork2.exceptions.ForbiddenAccessException;
import com.example.restfulapisocialnetwork2.exceptions.ResourceNotFoundException;
import com.example.restfulapisocialnetwork2.models.*;
import com.example.restfulapisocialnetwork2.repositories.FriendRepository;
import com.example.restfulapisocialnetwork2.repositories.RequestedFriendRepository;
import com.example.restfulapisocialnetwork2.repositories.UserInfoRepository;
import com.example.restfulapisocialnetwork2.repositories.UserRepository;
import com.example.restfulapisocialnetwork2.responses.*;
import jakarta.transaction.Transactional;
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
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserSession userSession;

    @Override
    public int SetRequestFriend(Long userIdFriend) throws Exception {
        Optional<User> userFriend = userRepository.findById(userIdFriend);
        if (userFriend.isEmpty()) {
            throw new ResourceNotFoundException("This user does not exist");
        }
        User user = userSession.GetUser();
        if (requestedFriendRepository.existsByUserIdAAndUserIdB(user.getId(), userIdFriend)
                || friendRepository.existsByUserIdAAndUserIdBOrUserIdAAndUserIdB(
                user.getId(), userIdFriend
                , userIdFriend, user.getId())
        ) {
            throw new DataAlreadyExistsException("This data exist");
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
            throw new ResourceNotFoundException("This user does not exist");
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
    @Transactional
    public void SetAcceptFriend(AcceptFriendDTO acceptFriendDTO) throws Exception {
        Optional<User> userFriendOptional = userRepository.findById(acceptFriendDTO.getUserId());
        if (userFriendOptional.isEmpty()) {
            throw new ResourceNotFoundException("This user does not exist");
        }
        User userFriend = userFriendOptional.get();
        User userLogin = userSession.GetUser();
        if (requestedFriendRepository.existsByUserIdAAndUserIdB(userLogin.getId(), userFriend.getId())) {
            requestedFriendRepository.deleteByUserIdAAndUserIdB(
                    userLogin.getId(), acceptFriendDTO.getUserId());
            if (acceptFriendDTO.getIsAccept() == 1) {
                Friend friend = Friend.builder()
                        .userIdA(userLogin.getId())
                        .userIdB(acceptFriendDTO.getUserId())
                        .build();
                friendRepository.save(friend);
            }
        } else {
            throw new ResourceNotFoundException("This request does not exist");
        }
    }

    @Override
    public FriendListResponse GetUserFriends(RequestFriendDTO requestFriendDTO)
            throws Exception {
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);

        Long idUserGetFriend = (requestFriendDTO.getUserId() == 0) ? userSession.GetUser().getId() : requestFriendDTO.getUserId();
        Page<Friend> pageResult =
                friendRepository.findByUserId(idUserGetFriend, pageRequest);
        if (pageResult.isEmpty()) {
            throw new ResourceNotFoundException("This user does not exist");
        }

        List<Friend> listFriend = pageResult.getContent();
        List<Friend> filteredPosts = listFriend.stream()
                .skip(requestFriendDTO.getIndex())
                .limit(requestFriendDTO.getCount())
                .collect(Collectors.toList());
        List<FriendResponse> listFriendResponse = new ArrayList<>();

        for (Friend rqFriend : filteredPosts) {
            Long idFriend = (rqFriend.getUserIdA() == idUserGetFriend) ? rqFriend.getUserIdB() : rqFriend.getUserIdA();
            Optional<User> userOptional = userRepository.findById(idFriend);
            User userFriends = userOptional.get();
            FriendResponse friendResponse = FriendResponse
                    .builder()
                    .id(userFriends.getId())
                    .userName(userFriends.getFullName())
                    .avatar(userFriends.getAddress())
                    .build();
            listFriendResponse.add(friendResponse);
        }

        return FriendListResponse.builder()
                .postResponseList(listFriendResponse)
                .countFriend(listFriendResponse.size())
                .build();
    }

    @Override
    public UserInfoResponse GetUserInfo(Long userIdFriend) throws Exception {
        User userLogin = userSession.GetUser();
        Long idUserGetInFor = (userIdFriend == 0) ? userLogin.getId() : userIdFriend;
        Optional<User> userOp = userRepository.findById(idUserGetInFor);
        if (userOp.isEmpty()) {
            throw new ResourceNotFoundException("This user does not exist");
        }
        Optional<UserInfo> userInfoOp = userInfoRepository.findByUserId(idUserGetInFor);
        UserInfo userInfo = new UserInfo();
        if (!userInfoOp.isEmpty()) {
            userInfo = userInfoOp.get();
            //throw  new ResourceNotFoundException("This user does not exist");
        }
        User user = userOp.get();
        long countFriend = friendRepository.countByUserIdA(userIdFriend);
        int isFriend = (friendRepository.existsByUserIdAAndUserIdBOrUserIdAAndUserIdB(
                user.getId(), userIdFriend
                , userIdFriend, user.getId())) ? 1 : 0;
        DataFriendUserDTO dataFriendUserDTO = DataFriendUserDTO.
                builder()
                .is_friend(isFriend)
                .listing(countFriend)
                .build();
        UserInfoResponse userInfoResponse = UserInfoResponse.
                fromUser(user, dataFriendUserDTO, userInfo);
        return userInfoResponse;
    }

}

