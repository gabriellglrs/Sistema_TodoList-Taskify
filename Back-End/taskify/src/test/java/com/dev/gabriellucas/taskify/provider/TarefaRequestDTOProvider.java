package com.dev.gabriellucas.taskify.provider;

import com.dev.gabriellucas.taskify.DTO.TarefaRequestDTO;
import com.dev.gabriellucas.taskify.enums.PrioridadeTarefa;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;

import java.time.LocalDateTime;

public class TarefaRequestDTOProvider {

    public static final Long ID = 1L;
    public static final String TITULO = "Guardar o carro";
    public static final String DESCRICAO = "Tirar o carro da rua";
    public static final LocalDateTime DATA_VENCIMENTO = LocalDateTime.now().plusDays(2);
    public static final StatusTarefa STATUS_TAREFA = StatusTarefa.PENDENTE;
    public static final PrioridadeTarefa PRIORIDADE_TAREFA = PrioridadeTarefa.URGENTE;

    public TarefaRequestDTO create() {
        TarefaRequestDTO request = new TarefaRequestDTO();
        request.setId(ID);
        request.setTitulo(TITULO);
        request.setDescricao(DESCRICAO);
        request.setDataVencimento(DATA_VENCIMENTO);
        request.setStatus(STATUS_TAREFA);
        request.setPrioridade(PRIORIDADE_TAREFA);
        return request;
    }
}
