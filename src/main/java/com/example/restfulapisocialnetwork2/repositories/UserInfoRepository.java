package com.example.restfulapisocialnetwork2.repositories;

import com.example.restfulapisocialnetwork2.models.User;
import com.example.restfulapisocialnetwork2.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUserId(Long userId);
}
