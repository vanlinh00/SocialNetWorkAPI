package com.example.restfulapisocialnetwork2.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="roles")
@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
}
