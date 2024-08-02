package com.example.restfulapisocialnetwork2.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PostDTO {
    @Column(name = "described")
    private String described;
}
