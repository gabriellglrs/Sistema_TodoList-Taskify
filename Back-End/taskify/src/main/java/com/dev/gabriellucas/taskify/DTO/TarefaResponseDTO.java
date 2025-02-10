package com.dev.gabriellucas.taskify.DTO;

import com.dev.gabriellucas.taskify.enums.PrioridadeTarefa;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TarefaResponseDTO implements Serializable {

    private Long id;

    private List<CategoriaIdDTO> categoria;

    private String titulo;

    private String descricao;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataVencimento;

    private PrioridadeTarefa prioridade;

    private StatusTarefa status;

}
