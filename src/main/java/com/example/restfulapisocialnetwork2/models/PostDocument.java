package com.example.restfulapisocialnetwork2.models;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDocument {
    @Id
    private String id;
    private Long userId;
    private String described;
}