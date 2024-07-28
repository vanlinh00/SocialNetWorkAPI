package com.example.restfulapisocialnetwork2.repositories;

import com.example.restfulapisocialnetwork2.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    List<VerificationCode> findByUserId(Long UserId);
}
