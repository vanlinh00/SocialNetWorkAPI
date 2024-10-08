package com.example.restfulapisocialnetwork2.models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "likes")
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")  // Cột khóa ngoại
    private Post post;
}
