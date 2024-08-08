package com.example.restfulapisocialnetwork2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class DeleteCommentDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("id_com")
    private Long idCom;
}
