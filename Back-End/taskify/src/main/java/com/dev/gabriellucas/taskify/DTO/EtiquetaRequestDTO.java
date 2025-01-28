package com.dev.gabriellucas.taskify.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EtiquetaRequestDTO {

    private Long id;

    @NotNull(message = "Não pode ser vazio.")
    @Size(min = 2, max = 30, message = "O Nome deve ter entre 2 a 30 caracteres.")
    private String nome;

    @NotNull(message = "Não pode ser vazio.")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "Cor deve estar no formato hexadecimal (#RRGGBB).")
    private String cor;
}
