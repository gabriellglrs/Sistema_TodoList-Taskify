package com.dev.gabriellucas.taskify.DTO;

import com.dev.gabriellucas.taskify.enums.StatusLista;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ListaRequestDTO {
    private Long id;

    @NotNull(message = "O título não pode ser vazio.")
    @Size(min = 1, max = 100, message = "O título deve ter entre 1 e 100 caracteres.")
    private String titulo;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
    private String descricao;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @NotNull(message = "O status não pode ser nulo.")
    private StatusLista status;

}
