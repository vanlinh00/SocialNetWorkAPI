package com.example.restfulapisocialnetwork2.dtos;

import jakarta.persistence.Column;

public class PostDTO {
    @Column(name = "described")
    private String described;
}
