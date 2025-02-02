package com.dev.gabriellucas.taskify.provider;

import com.dev.gabriellucas.taskify.DTO.TarefaResponseDTO;
import com.dev.gabriellucas.taskify.enums.PrioridadeTarefa;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;

import java.time.LocalDateTime;

public class TarefaResponseDTOProvider {

    public static final Long ID = 1L;
    public static final String TITULO = "Guardar o carro";
    public static final String DESCRICAO = "Tirar o carro da rua";
    public static final LocalDateTime DATA_CRIACAO = LocalDateTime.now();
    public static final LocalDateTime DATA_VENCIMENTO = LocalDateTime.now().plusDays(2);
    public static final StatusTarefa STATUS_TAREFA = StatusTarefa.PENDENTE;
    public static final PrioridadeTarefa PRIORIDADE_TAREFA = PrioridadeTarefa.URGENTE;

    public TarefaResponseDTO create(){
        TarefaResponseDTO tarefaResponseDTO = new TarefaResponseDTO();
        tarefaResponseDTO.setId(ID);
        tarefaResponseDTO.setTitulo(TITULO);
        tarefaResponseDTO.setDescricao(DESCRICAO);
        tarefaResponseDTO.setDataVencimento(DATA_VENCIMENTO);
        tarefaResponseDTO.setStatus(STATUS_TAREFA);
        tarefaResponseDTO.setPrioridade(PRIORIDADE_TAREFA);
        tarefaResponseDTO.setDataCriacao(DATA_CRIACAO);
        return tarefaResponseDTO;
    }
}
