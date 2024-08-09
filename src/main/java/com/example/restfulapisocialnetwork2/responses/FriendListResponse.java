package com.example.restfulapisocialnetwork2.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Setter
@Getter
@Data
public class FriendListResponse {
    private List<FriendResponse> postResponseList;
    @JsonProperty("count_friend")
    private int countFriend;
}
