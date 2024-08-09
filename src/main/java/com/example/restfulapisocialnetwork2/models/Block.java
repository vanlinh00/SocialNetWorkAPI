package com.example.restfulapisocialnetwork2.models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "blocks")
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "blocked_user_id")
    private Long blockedUserId;
}
