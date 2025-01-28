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

     @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
     private String nome;

     @Size(max = 200, message = "A descrição não pode ter mais de 200 caracteres")
     private String descricao;

     @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "A cor deve estar no formato hexadecimal válido (#RRGGBB)")
     private String cor;
}
