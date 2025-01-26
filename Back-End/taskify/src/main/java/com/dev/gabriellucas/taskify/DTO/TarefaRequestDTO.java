package com.dev.gabriellucas.taskify.DTO;

import com.dev.gabriellucas.taskify.enums.PrioridadeTarefa;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TarefaRequestDTO {

    private Long id;

    @NotNull(message = "O título não pode ser vazio.")
    @Size(min = 1, max = 100, message = "O título deve ter entre 1 e 200 caracteres.")
    private String titulo;

    @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres.")
    private String descricao;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime dataVencimento;

    @NotNull(message = "A prioridade não pode ser vazia.")
    private PrioridadeTarefa prioridade;

    @NotNull(message = "O status não pode ser vazio.")
    private StatusTarefa status;
}
