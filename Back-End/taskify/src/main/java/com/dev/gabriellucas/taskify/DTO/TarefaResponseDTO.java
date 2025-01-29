package com.dev.gabriellucas.taskify.DTO;

import com.dev.gabriellucas.taskify.entities.*;
import com.dev.gabriellucas.taskify.enums.PrioridadeTarefa;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class TarefaResponseDTO {

    private Long id;

    private List<CategoriaIdDTO> categoria;

    private String titulo;

    private String descricao;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataVencimento;

    private PrioridadeTarefa prioridade;

    private StatusTarefa status;

}
