package com.dev.gabriellucas.taskify.DTO;

import lombok.*;

import java.time.Instant;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ErroResponseDTO {
     private Instant timestamp;
     private Integer status;
     private String code;
     private String message;
     private String details;
     private String path;
}
