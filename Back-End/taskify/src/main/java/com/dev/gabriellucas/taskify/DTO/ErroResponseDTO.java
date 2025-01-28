package com.dev.gabriellucas.taskify.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ErroResponseDTO {

     @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
     private LocalDateTime timestamp;
     private Integer status;
     private String code;
     private String message;
     private String details;
     private String path;
}
