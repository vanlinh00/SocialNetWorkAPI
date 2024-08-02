package com.example.restfulapisocialnetwork2.responses;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import java.time.LocalDateTime;

//@Data
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@MappedSuperclass
//@Builder
public class BaseReponse {
    @JsonProperty("created_at")
    private LocalDateTime createAt;

    @JsonProperty("updated_at")
    private LocalDateTime updateAt;
}
