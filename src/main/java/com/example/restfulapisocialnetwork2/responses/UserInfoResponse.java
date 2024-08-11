package com.example.restfulapisocialnetwork2.responses;

import com.example.restfulapisocialnetwork2.dtos.DataFriendUserDTO;
import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.models.UserInfo;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Data
public class UserInfoResponse {
    Long id;
    String user_name;
    LocalDateTime created;
    String description;
    String avatar;
    String cover_image;
    String link;    // Liên kết
    String address;
    String city;
    String country;
    String listing;  // Số lượng bạn bè của user
    String is_friend;   // Người dùng hiện tại có phải là bạnh ko
    String online;  // 1 on, 0 of

    public static UserInfoResponse fromUser(User user, DataFriendUserDTO dataFriendUserDTO, UserInfo userInfo) {
        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .id(user.getId())
                .user_name(user.getFullName())
                .created(user.getCreatedAt())
                .description(userInfo.getDescription())
                .avatar(userInfo.getAvatar())
                .cover_image(userInfo.getCoverImage())
                .link(userInfo.getLink())
                .address(user.getAddress())
                .city(userInfo.getCity())
                .country(userInfo.getCountry())
                .listing(String.valueOf(dataFriendUserDTO.getListing()))
                .is_friend(String.valueOf(dataFriendUserDTO.getListing()))
                .online("1")
                .build();
        return userInfoResponse;
    }

}

