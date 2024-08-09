package com.example.restfulapisocialnetwork2.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id_A")
    private Long userIdA;

    @Column(name = "user_id_B")
    private Long userIdB;
}
