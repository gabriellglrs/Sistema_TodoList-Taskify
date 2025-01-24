package com.dev.gabriellucas.taskify.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioResponseDTO {
     private Long id;
     private String nome;
     private String email;
     private LocalDateTime dataCadastro;
}
