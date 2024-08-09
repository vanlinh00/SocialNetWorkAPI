package com.example.restfulapisocialnetwork2.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")  // Cột khóa ngoại
    private Post post;

    @Column(name = "link_image", length = 300)
    private String linkImage;
}
