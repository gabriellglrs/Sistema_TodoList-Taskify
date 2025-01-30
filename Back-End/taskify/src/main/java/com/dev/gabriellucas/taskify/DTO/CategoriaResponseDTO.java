package com.dev.gabriellucas.taskify.DTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoriaResponseDTO {
     private Long id;
     private String nome;
     private String descricao;
     private String cor;
}
